package spbau.malysheva.roguelike.model

/**
 * General entity class.
 *
 * @see EntityFactory
 * @see EntityVisitor
 */
abstract class Entity() {

    /**
     * The [World] instance which this entity belongs
     */
    var owner: World? = null

    /**
     * The [Field] where entity is placed
     */
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

    /**
     * Accepts the [visitor].
     *
     * In fact, call the proper [visitor] method according to the real type of this instance
     */
    open fun <R> accept(visitor: EntityVisitor<R>) = visitor.visitDefault(this)
}