package spbau.malysheva.roguelike.model


abstract class Entity() {

    var owner: World? = null

    var field: Field? = null
        private set

    internal fun attach(field: Field) {
        assert(this.field == null)
        this.field = field
        field.entity = this
    }

    internal fun detach() {
        field = null
    }

    internal open fun getNextMoveDirection() = Direction.NOPE

    internal open fun interact(other: Entity) {
    }

    open fun <R> accept(visitor: EntityVisitor<R>) = visitor.visitDefault(this)
}