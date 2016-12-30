package spbau.malysheva.roguelike.model

import java.util.*


val ENTITY_RANDOM = Random()

val DEFAULT_STRATEGY: Creature.() -> Direction = {
    Direction.NOPE
}

val DEFAULT_HEALTH = 10
val DEFAULT_DAMAGE = 3
val DEFAULT_LUCK = 0

open class Creature(initHealth: Int = DEFAULT_HEALTH,
                    initDamage: Int = DEFAULT_DAMAGE,
                    initLuck: Int = DEFAULT_LUCK) : Entity() {

    var health = initHealth
    var damage = initDamage
    var luck = initLuck

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

class Player(initHealth: Int = DEFAULT_HEALTH,
             initDamage: Int = DEFAULT_DAMAGE,
             initLuck: Int = DEFAULT_LUCK)
    : Creature(initHealth, initDamage, initLuck) {

    override fun <R> accept(visitor: EntityVisitor<R>) = visitor.visitPlayer(this)
}

class Space() : Entity() {

    override fun <R> accept(visitor: EntityVisitor<R>) = visitor.visitSpace(this)
}

class Block() : Entity() {

    override fun <R> accept(visitor: EntityVisitor<R>) = visitor.visitBlock(this)
}

// factories

abstract class AbstractCreatureFactory : EntityFactory {

    override abstract fun create(): Creature
}

fun AbstractCreatureFactory.withStrategy(strategy: (Creature) -> Direction) : AbstractCreatureFactory {
    return object : AbstractCreatureFactory(){

        override fun create(): Creature {
            val creature = this@withStrategy.create()
            creature.moveStrategy = strategy
            return creature
        }
    }
}

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

object CreatureFactory : AbstractCreatureFactory() {
    override fun create() = Creature()
}

object PlayerFactory : AbstractCreatureFactory() {
    override fun create() = Player()
}

object SpaceFactory : EntityFactory {
    override fun create() = Space()
}

object BlockFactory : EntityFactory {
    override fun create() = Block()
}
