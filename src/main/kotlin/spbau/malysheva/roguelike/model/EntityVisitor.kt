package spbau.malysheva.roguelike.model

/**
 * Visitor pattern implemented on [Entity] subclasses.
 *
 * @see Entity.accept
 */
abstract class EntityVisitor<R> {

    /**
     * Visits [Player] instance
     */
    open fun visitPlayer(player: Player) = visitCreature(player)

    /**
     * Visits [Creature] instance
     */
    open fun visitCreature(creature: Creature) = visitDefault(creature)

    /**
     * Visits [Space] instance
     */
    open fun visitSpace(space: Space) = visitDefault(space)

    /**
     * Visits [Block] instance
     */
    open fun visitBlock(block: Block) = visitDefault(block)

    abstract fun visitDefault(entity: Entity) : R
}

/**
 * The simplest implementation of [EntityVisitor]
 */
open class SimpleEntityVisitor() : EntityVisitor<Unit>() {
    override fun visitDefault(entity: Entity) {
    }
}