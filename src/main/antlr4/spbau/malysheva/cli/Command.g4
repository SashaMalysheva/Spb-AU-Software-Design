grammar Command;

@header{
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 *  CLI Parser Generator via ANTLR4
 *
 *  It parses Command Lines.
 *  Command Line is a set of commands seperated by '|' symbol,
 *  or a special environment command 'key=value'
 *  Each command is set of words, strings or macros.
 *  Macro starts with '$' symbol.
 *  Macros are being expanded on parsing.
 *
 *  Parser will be generated accroding to grammar below.
 */
}

command_line returns[List<Command> commands]
    :
    | EOF { $commands = Collections.emptyList(); }
    | piped_commands { $commands = $piped_commands.commands; }
    | enviroment_command { $commands = Arrays.asList($enviroment_command.cmd); }
    ;

piped_commands returns[List<Command> commands]
    : fst=command { $commands = new ArrayList<>(); $commands.add($fst.cmd); }
    ( PIPE nxt=command { $commands.add($nxt.cmd); })*
    ;

command returns[Command cmd]
    : fst=m_argument { List<String> args = new ArrayList<>($fst.text); }
    ( nxt=m_argument { args.addAll($nxt.text); })*
    { $cmd = CommandManager.INSTANCE.createCommand(args); }
    ;

enviroment_command returns[Command cmd] locals[String txt]
    : STRING EQUALITY
    ( argument { $txt = $argument.text; }
    | macros   { $txt = $macros.text; }
    ) { $cmd = new EnvironmentCommand($STRING.text, $txt); }
    ;

m_argument returns[List<String> text]
    : macros { $text = Arrays.asList($macros.text.split(" "));
               $text.removeIf((s) -> s.isEmpty()); }
    | argument { $text = Arrays.asList($argument.text); }
    ;

argument returns[String text]
    : STRING { $text = $STRING.text; }
    | strong_quotation { $text = $strong_quotation.text; }
    | weak_quotation { $text = $weak_quotation.text; }
    ;

macros returns[String text]
    : DOLLAR STRING { $text = Environment.INSTANCE.get($STRING.text); }
    ;

strong_quotation returns[String text]
    : STRONG_QUOTE { StringBuilder sb = new StringBuilder(); }
    ( strong_quotation_content { sb.append($strong_quotation_content.text); }
    )* STRONG_QUOTE { $text = sb.toString(); }
    ;

weak_quotation returns[String text]
    : WEAK_QUOTE { StringBuilder sb = new StringBuilder(); }
    ( weak_quotation_content { sb.append($weak_quotation_content.text); }
    | macros { sb.append($macros.text); }
    )* WEAK_QUOTE { $text = sb.toString(); }
    ;

strong_quotation_content : ~(STRONG_QUOTE | '\n' | '\r' );
weak_quotation_content : ~(WEAK_QUOTE | '\n' | '\r' | DOLLAR);

STRING          : [\-\./a-zA-z0-9]+;
WEAK_QUOTE      : '"';
STRONG_QUOTE    : '\'';
DOLLAR          : '$';
EQUALITY        : '=';
PIPE            : '|';
WS              : [\t\r ] -> skip;
