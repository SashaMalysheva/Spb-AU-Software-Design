package spbau.malysheva.roguelike.model

import java.util.*


val ENTITY_RANDOM = Random()

val DEFAULT_STRATEGY: Creature.() -> Direction = {
    Direction.NOPE
}

val DEFAULT_HEALTH = 10
val DEFAULT_DAMAGE = 3
val DEFAULT_LUCK = 0

/**
 * The movable 'live' entity.
 *
 * @param initHealth the health of the entity on the creation
 * @param initDamage the damage power of the entity on the creation
 * @param initLuck the luck of the entity on the creation
 */
open class Creature(initHealth: Int = DEFAULT_HEALTH,
                    initDamage: Int = DEFAULT_DAMAGE,
                    initLuck: Int = DEFAULT_LUCK) : Entity() {

    /**
     * the health of the creature. Will decrease of the entity will be attacked by another.
     * If becomes less then 0, the entity will be removed from the world
     */
    var health = initHealth
    /**
     * the constant damage power of the creature.
     */
    var damage = initDamage
    /**
     * the probable damage power increase of the creature.
     */
    var luck = initLuck

    /**
     * the move strategy of th creature.
     * use it to move creatures on the world map.
     */
    var moveStrategy = DEFAULT_STRATEGY

    override fun getNextMoveDirection() = moveStrategy(this)

    override fun <R> accept(visitor: EntityVisitor<R>) = visitor.visitCreature(this)

    val CREATURE_INTERACTIONS = object : SimpleEntityVisitor() {
        override fun visitCreature(creature: Creature) {
            val thisDamage = damage + ENTITY_RANDOM.nextInt(luck + 1)
            val thatDamage = creature.damage + ENTITY_RANDOM.nextInt(creature.luck + 1)
            health -= thatDamage
            creature.health -= thisDamage
            if (creature.health < 1) {
                val creatureField = creature.field ?: return
                val field = field ?: return
                detach()
                creature.detach()
                attach(creatureField)
                Space().attach(field)
            }
            if (health < 1) {
                val field = field ?: return
                detach()
                Space().attach(field)
            }
        }

        override fun visitSpace(space: Space) {
            val field = field ?: return
            val spaceField = space.field ?: return
            detach()
            space.detach()
            attach(spaceField)
            space.attach(field)
        }
    }

    override fun interact(other: Entity) {
        other.accept(CREATURE_INTERACTIONS)
    }
}

/**
 * The creature that belongs to user. One per world.
 */
class Player(initHealth: Int = DEFAULT_HEALTH,
             initDamage: Int = DEFAULT_DAMAGE,
             initLuck: Int = DEFAULT_LUCK)
    : Creature(initHealth, initDamage, initLuck) {

    override fun <R> accept(visitor: EntityVisitor<R>) = visitor.visitPlayer(this)
}

/**
 * the empty space on the world map
 */
class Space() : Entity() {

    override fun <R> accept(visitor: EntityVisitor<R>) = visitor.visitSpace(this)
}

/**
 * the obstacle on the map
 */
class Block() : Entity() {

    override fun <R> accept(visitor: EntityVisitor<R>) = visitor.visitBlock(this)
}

// factories

/**
 * Base creature factory class.
 *
 * @see withStrategy
 * @see withParams
 */
abstract class AbstractCreatureFactory : EntityFactory {

    override abstract fun create(): Creature
}

/**
 * Adds a initial [strategy] parameter to the creature factory.
 */
fun AbstractCreatureFactory.withStrategy(strategy: (Creature) -> Direction) : AbstractCreatureFactory {
    return object : AbstractCreatureFactory(){

        override fun create(): Creature {
            val creature = this@withStrategy.create()
            creature.moveStrategy = strategy
            return creature
        }
    }
}

/**
 * Adds intial basic parameters to the creature factory
 */
fun AbstractCreatureFactory.withParams(health: Int = -1, damage: Int = -1, luck: Int = -1) : AbstractCreatureFactory{
    return object : AbstractCreatureFactory(){

        override fun create(): Creature {
            val creature = this@withParams.create()
            if (health >= 0) {
                creature.health = health
            }
            if (damage >= 0) {
                creature.damage = damage
            }
            if (luck >= 0) {
                creature.luck = luck
            }
            return creature
        }
    }
}

/**
 * Base creature factory
 */
object CreatureFactory : AbstractCreatureFactory() {
    override fun create() = Creature()
}

/**
 * Base player factory
 */
object PlayerFactory : AbstractCreatureFactory() {
    override fun create() = Player()
}

/**
 * Base space factory
 */
object SpaceFactory : EntityFactory {
    override fun create() = Space()
}

/**
 * Base block factory
 */
object BlockFactory : EntityFactory {
    override fun create() = Block()
}
