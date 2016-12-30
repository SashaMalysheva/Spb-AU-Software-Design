package spbau.malysheva.roguelike.model


class Field(val x: Int,
            val y: Int,
            val world: World) {

    var entity : Entity? = null
        internal set
}