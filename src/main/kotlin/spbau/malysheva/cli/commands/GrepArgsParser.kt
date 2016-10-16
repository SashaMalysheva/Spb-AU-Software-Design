package spbau.malysheva.cli.commands

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

val registerSensitivity = Option.builder("i").desc("ignore case distinctions").build()!!
val printedLinesCount = Option.builder("A").argName("NUM").hasArg().desc("print NUM lines after matched content").build()!!
val forceWordMatching = Option.builder("w").desc("force a pattern to match a whole word").build()!!

val grepOptions = Options()
        .addOption(registerSensitivity)
        .addOption(printedLinesCount)
        .addOption(forceWordMatching)!!

data class GrepArguments(val insensivity: Boolean,
                         val printedLines : Int,
                         val forceWordMatching : Boolean,
                         val regex : String
)

fun getGrepArguments(args : Array<String>) : GrepArguments {
    val cl = DefaultParser().parse(grepOptions, args)
    return GrepArguments(
            !cl.hasOption('i'),
            if (cl.hasOption('A')) Integer.parseInt(cl.getOptionValue('A')) else 1,
            cl.hasOption('w'),
            if (!cl.args.isEmpty()) cl.args[0] else throw IllegalArgumentException("regex is missing")
        )
}
