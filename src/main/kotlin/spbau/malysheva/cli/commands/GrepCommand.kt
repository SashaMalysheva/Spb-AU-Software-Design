package spbau.malysheva.cli.commands

import spbau.malysheva.cli.Command
import java.io.InputStream
import java.util.*

/**
 * GrepCommand.
 *
 * On executing, it returns all lines that matches to given regular expression.
 * @param args arguments of the command
 */

class GrepCommand(val args: GrepArguments) : Command {
    /**
     * Returns lines that matches to given the regular expression.
     *
     * @param stream input lines
     * @return a stream that contains result
     */
    override fun execute(stream: InputStream): InputStream {
        val lines = stream.reader().readLines()
        val sb = StringBuilder()
        val regexOpt = HashSet<RegexOption>()
        if (args.insensivity) {
            regexOpt.add(RegexOption.IGNORE_CASE)
        }
        val regex = Regex(args.regex, regexOpt)
        lines.forEachIndexed { i, s ->
            var res = regex.find(s)
            if (args.forceWordMatching) {
                while (res != null) {
                    if ((res.range.start != 0 && s[res.range.start - 1].isLetterOrDigit()) ||
                            (res.range.last + 1 != s.length && s[res.range.last + 1].isLetterOrDigit())) {
                        res = res.next()
                    } else {
                        break
                    }
                }
            }
            if (res != null) {
                for(k in i until i + args.printedLines) {
                    sb.append(lines[k])
                    sb.append('\n')
                }
            }
        }
        return sb.toString().byteInputStream()
    }
}
