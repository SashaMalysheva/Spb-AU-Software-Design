package spbau.malysheva.cli.commands

import spbau.malysheva.cli.Command
import java.io.InputStream
import java.util.*

class GrepCommand(val args: GrepArguments) : Command {
    override fun execute(stream: InputStream): InputStream {
        val lines = stream.reader().readLines()
        val sb = StringBuilder()
        val regexOpt = HashSet<RegexOption>()
        if (args.insensivity) {
            regexOpt.add(RegexOption.IGNORE_CASE)
        }
        val regex = Regex(args.regex, regexOpt)
        lines.forEachIndexed { i, s ->
            var res = regex.matchEntire(s)
            while (res != null) {
                if (!args.forceWordMatching
                        || (s[res.range.start - 1] == ' ' && s[res.range.last + 1] == ' ')) {
                    for(k in i until i + args.printedLines) {
                        sb.append(lines[k])
                        sb.append('\n')
                    }
                    break
                }
                res = res.next()
            }
        }
        return sb.toString().byteInputStream()
    }
}