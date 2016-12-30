package spbau.malysheva.roguelike.view

import spbau.malysheva.roguelike.model.*

/**
 * Provides mapping between entity and its presentation on the screen
 *
 * @see EntityVisitor
 */
object EntityPresenationProvider : EntityVisitor<Char>() {

    override fun visitPlayer(player: Player) = 'p'

    override fun visitCreature(creature: Creature) = 'm'

    override fun visitSpace(space: Space) = ' '

    override fun visitBlock(block: Block) = '#'

    override fun visitDefault(entity: Entity) = '?'
}