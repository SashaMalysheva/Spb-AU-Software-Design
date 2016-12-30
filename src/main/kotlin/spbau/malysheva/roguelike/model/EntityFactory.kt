package spbau.malysheva.roguelike.model;

/**
 * Entity factory interface.
 *
 * @see Entity
 */
interface EntityFactory {

    /**
     * Creates [Entity] instance
     */
    fun create() : Entity
}
