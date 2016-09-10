package shell.syntax;

import org.junit.Test;
import shell.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by the7winds on 10.09.16.
 */

public class TokenizerTest {

    @Test
    public void summarizeTest() throws SyntaxException {
        Environment.getInstance().variableAssignment("a", "4");
        Environment.getInstance().variableAssignment("b", "2");
        Environment.getInstance().variableAssignment("c", "42");

        List<Token> tokens = Tokenizer.tokenize(Tokenizer.rawTokenize("echo a$b | cat | echo $c'$c' $c | wc"));

        assertEquals(Arrays.asList(Token.Type.CMD, Token.Type.ARG,
                Token.Type.PIPE, Token.Type.CMD,
                Token.Type.PIPE, Token.Type.CMD, Token.Type.ARG, Token.Type.ARG,
                Token.Type.PIPE, Token.Type.CMD),
                tokens.stream()
                        .map(Token::getType)
                        .collect(Collectors.toList()));
    }

    @Test(expected = SyntaxException.class)
    public void failTest() throws SyntaxException {
        Environment.getInstance().variableAssignment("a", "4");
        Environment.getInstance().variableAssignment("b", "2");
        Environment.getInstance().variableAssignment("c", "42");

        Tokenizer.tokenize(Tokenizer.rawTokenize("echo \'a\"$b | cat | echo $c'$c' $c | wc"));
    }

    @Test
    public void tokenize() throws Exception {
        Environment.getInstance().variableAssignment("a", "42");

        assertEquals(Arrays.asList(Token.Type.CMD, Token.Type.ARG),
                Tokenizer.tokenize(Arrays.asList(new RawToken("echo"), new RawToken("$a")))
                        .stream()
                        .map(Token::getType)
                        .collect(Collectors.toList()));

        assertEquals(Arrays.asList(Token.Type.CMD, Token.Type.ARG, Token.Type.PIPE, Token.Type.CMD),
                Tokenizer.tokenize(Arrays.asList(new RawToken("echo"), new RawToken("$a"), new RawToken("|"), new RawToken("cat")))
                        .stream()
                        .map(Token::getType)
                        .collect(Collectors.toList()));
    }

    @Test
    public void expand() throws Exception {
        Environment.getInstance().variableAssignment("a", "42");

        assertEquals("42", Tokenizer.expand("$a"));
        assertEquals("$a", Tokenizer.expand("'$a'"));

        Environment.getInstance().variableAssignment("a", "4");
        Environment.getInstance().variableAssignment("b", "2");

        assertEquals("42", Tokenizer.expand("$a$b"));
        assertEquals("4$b", Tokenizer.expand("$a'$'b"));
        assertEquals("4$b", Tokenizer.expand("$a'$'b"));
    }

    @Test
    public void rawTokenise() throws Exception {
        List<RawToken> rawTokenList;

        rawTokenList = Tokenizer.rawTokenize("echo a");
        assertEquals(2, rawTokenList.size());

        rawTokenList = Tokenizer.rawTokenize("ec\"ho\" a");
        assertEquals(2, rawTokenList.size());

        rawTokenList = Tokenizer.rawTokenize("ec\"ho\" \'a b c\'");
        assertEquals(2, rawTokenList.size());

        rawTokenList = Tokenizer.rawTokenize("ec\"ho\" a b    c");
        assertEquals(4, rawTokenList.size());

        rawTokenList = Tokenizer.rawTokenize("ec\"ho\" a|cat");
        assertEquals(4, rawTokenList.size());

        rawTokenList = Tokenizer.rawTokenize("ec\"ho\" a|c'a't");
        assertEquals(4, rawTokenList.size());
    }
}