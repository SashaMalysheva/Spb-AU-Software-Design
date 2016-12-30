package spbau.malysheva.roguelike.model


abstract class EntityVisitor<R> {

    open fun visitPlayer(player: Player) = visitCreature(player)

    open fun visitCreature(creature: Creature) = visitDefault(creature)

    open fun visitSpace(space: Space) = visitDefault(space)

    open fun visitBlock(block: Block) = visitDefault(block)

    abstract fun visitDefault(entity: Entity) : R
}

open class SimpleEntityVisitor() : EntityVisitor<Unit>() {
    override fun visitDefault(entity: Entity) {
    }
}