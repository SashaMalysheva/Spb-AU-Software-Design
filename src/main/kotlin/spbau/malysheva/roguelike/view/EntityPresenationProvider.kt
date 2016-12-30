package spbau.malysheva.roguelike.view

import spbau.malysheva.roguelike.model.*

object EntityPresenationProvider : EntityVisitor<Char>() {

    override fun visitPlayer(player: Player) = 'p'

    override fun visitCreature(creature: Creature) = 'm'

    override fun visitSpace(space: Space) = ' '

    override fun visitBlock(block: Block) = '#'

    override fun visitDefault(entity: Entity) = '?'
}