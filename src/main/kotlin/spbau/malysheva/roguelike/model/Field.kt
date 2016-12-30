package spbau.malysheva.roguelike.model

/**
 * Represents field on the map
 *
 * @property x x coordinate
 * @property y y coordinate
 * @property world world what map is containing this field
 */
class Field(val x: Int,
            val y: Int,
            val world: World) {

    /**
     * [entity] on the field
     */
    var entity : Entity? = null
        internal set
}