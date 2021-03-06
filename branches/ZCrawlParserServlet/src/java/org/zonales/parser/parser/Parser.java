/* -----------------------------------------------------------------------------
 * Parser.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.0
 * Produced : Wed Aug 03 09:32:54 ART 2011
 *
 * -----------------------------------------------------------------------------
 */
package org.zonales.parser.parser;

import java.util.Stack;
import java.util.Properties;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zonales.errors.ZMessages;
import org.zonales.metadata.ZCrawling;

public class Parser {

    private Parser() {
    }

    static public void main(ZCrawling zcrawling, PrintWriter out, String[] args) throws Exception {
        Properties arguments = new Properties();
        String error = "";
        boolean ok = args.length > 0;

        if (ok) {
            arguments.setProperty("Trace", "Off");
            arguments.setProperty("Rule", "zcrawling");

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-trace")) {
                    arguments.setProperty("Trace", "On");
                } else if (args[i].equals("-visitor")) {
                    arguments.setProperty("Visitor", args[++i]);
                } else if (args[i].equals("-file")) {
                    arguments.setProperty("File", args[++i]);
                } else if (args[i].equals("-string")) {
                    arguments.setProperty("String", args[++i]);
                } else if (args[i].equals("-rule")) {
                    arguments.setProperty("Rule", args[++i]);
                } else {
                    error = "unknown argument: " + args[i];
                    ok = false;
                }
            }
        }

        if (ok) {
            if (arguments.getProperty("File") == null
                    && arguments.getProperty("String") == null) {
                error = "insufficient arguments: -file or -string required";
                ok = false;
            }
        }

        if (!ok) {
            out.println(ZMessages.ZPARSER_USE_ERROR);
        } else {
            try {
                Rule rule = null;

                if (arguments.getProperty("File") != null) {
                    rule =
                            parse(
                            arguments.getProperty("Rule"),
                            new File(arguments.getProperty("File")),
                            arguments.getProperty("Trace").equals("On"),
                            zcrawling);
                } else if (arguments.getProperty("String") != null) {
                    rule =
                            parse(
                            arguments.getProperty("Rule"),
                            arguments.getProperty("String"),
                            arguments.getProperty("Trace").equals("On"),
                            zcrawling);
                }

                if (arguments.getProperty("Visitor") != null) {
                    Visitor visitor =
                            (Visitor) Class.forName(arguments.getProperty("Visitor")).newInstance();
                    rule.accept(zcrawling, visitor);
                }

            } catch (ClassNotFoundException e) {
                out.println(ZMessages.ZPARSER_PARSE_VISITOR_ERROR);
            } catch (IllegalAccessException e) {
                out.println(ZMessages.ZPARSER_PARSE_VISITOR_ILEGAL_ERROR);
            } catch (InstantiationException e) {
                out.println(ZMessages.ZPARSER_PARSE_VISITOR_INSTANT_ERROR);
            }
        }
    }

    static public Rule parse(String rulename, String string, ZCrawling zcrawling)
            throws IllegalArgumentException,
            ParserException {
        return parse(rulename, string, false, zcrawling);
    }

    static public Rule parse(String rulename, InputStream in, ZCrawling zcrawling)
            throws IllegalArgumentException,
            IOException,
            ParserException {
        return parse(rulename, in, false, zcrawling);
    }

    static public Rule parse(String rulename, File file, ZCrawling zcrawling)
            throws IllegalArgumentException,
            IOException,
            ParserException {
        return parse(rulename, file, false, zcrawling);
    }

    static private Rule parse(String rulename, String string, boolean trace, ZCrawling zcrawling)
            throws IllegalArgumentException,
            ParserException {
        if (rulename == null) {
            throw new IllegalArgumentException("null rulename");
        }
        if (string == null) {
            throw new IllegalArgumentException("null string");
        }

        ParserContext context = new ParserContext(string, trace, zcrawling);

        Logger.getLogger(Parser.class.getName()).log(Level.INFO, "RULENAME: {0}", new Object[]{rulename});
        Rule rule = null;
        if (rulename.equalsIgnoreCase("zcrawling")) {
            rule = Rule$zcrawling.parse(context);
        } else if (rulename.equalsIgnoreCase("descripcion")) {
            rule = Rule$descripcion.parse(context);
        } else if (rulename.equalsIgnoreCase("localidad")) {
            rule = Rule$localidad.parse(context);
        } else if (rulename.equalsIgnoreCase("tags")) {
            rule = Rule$tags.parse(context);
        } else if (rulename.equalsIgnoreCase("tag")) {
            rule = Rule$tag.parse(context);
        } else if (rulename.equalsIgnoreCase("fuente")) {
            rule = Rule$fuente.parse(context);
        } else if (rulename.equalsIgnoreCase("uri_fuente")) {
            rule = Rule$uri_fuente.parse(context);
        } else if (rulename.equalsIgnoreCase("criterios")) {
            rule = Rule$criterios.parse(context);
        } else if (rulename.equalsIgnoreCase("criterio")) {
            rule = Rule$criterio.parse(context);
        } else if (rulename.equalsIgnoreCase("deLosUsuarios")) {
            rule = Rule$deLosUsuarios.parse(context);
        } else if (rulename.equalsIgnoreCase("amigosDelUsuario")) {
            rule = Rule$amigosDelUsuario.parse(context);
        } else if (rulename.equalsIgnoreCase("siosi")) {
            rule = Rule$siosi.parse(context);
        } else if (rulename.equalsIgnoreCase("usuarios")) {
            rule = Rule$usuarios.parse(context);
        } else if (rulename.equalsIgnoreCase("commenters")) {
            rule = Rule$commenters.parse(context);
        } else if (rulename.equalsIgnoreCase("usuario")) {
            rule = Rule$usuario.parse(context);
        } else if (rulename.equalsIgnoreCase("palabras")) {
            rule = Rule$palabras.parse(context);
        } else if (rulename.equalsIgnoreCase("palabra")) {
            rule = Rule$palabra.parse(context);
        } else if (rulename.equalsIgnoreCase("filtros")) {
            rule = Rule$filtros.parse(context);
        } else if (rulename.equalsIgnoreCase("filtro")) {
            rule = Rule$filtro.parse(context);
        } else if (rulename.equalsIgnoreCase("listaNegraUsuarios")) {
            rule = Rule$listaNegraUsuarios.parse(context);
        } else if (rulename.equalsIgnoreCase("listaNegraPalabras")) {
            rule = Rule$listaNegraPalabras.parse(context);
        } else if (rulename.equalsIgnoreCase("minActions")) {
            rule = Rule$minActions.parse(context);
        } else if (rulename.equalsIgnoreCase("min-num-shuld-match")) {
            rule = Rule$min_num_shuld_match.parse(context);
        } else if (rulename.equalsIgnoreCase("cadena")) {
            rule = Rule$cadena.parse(context);
        } else if (rulename.equalsIgnoreCase("ALPHA")) {
            rule = Rule$ALPHA.parse(context);
        } else if (rulename.equalsIgnoreCase("DQUOTE")) {
            rule = Rule$DQUOTE.parse(context);
        } else if (rulename.equalsIgnoreCase("QUOTE")) {
            rule = Rule$QUOTE.parse(context);
        } else if (rulename.equalsIgnoreCase("mspace")) {
            rule = Rule$mspace.parse(context);
        } else if (rulename.equalsIgnoreCase("space")) {
            rule = Rule$space.parse(context);
        } else if (rulename.equalsIgnoreCase("comma")) {
            rule = Rule$comma.parse(context);
        } else if (rulename.equalsIgnoreCase("DIGIT")) {
            rule = Rule$DIGIT.parse(context);
        } else if (rulename.equalsIgnoreCase("HEXDIG")) {
            rule = Rule$HEXDIG.parse(context);
        } else if (rulename.equalsIgnoreCase("int")) {
            rule = Rule$int.parse(context);
        } else if (rulename.equalsIgnoreCase("URI")) {
            rule = Rule$URI.parse(context);
        } else if (rulename.equalsIgnoreCase("hier-part")) {
            rule = Rule$hier_part.parse(context);
        } else if (rulename.equalsIgnoreCase("URI-reference")) {
            rule = Rule$URI_reference.parse(context);
        } else if (rulename.equalsIgnoreCase("absolute-URI")) {
            rule = Rule$absolute_URI.parse(context);
        } else if (rulename.equalsIgnoreCase("relative-ref")) {
            rule = Rule$relative_ref.parse(context);
        } else if (rulename.equalsIgnoreCase("relative-part")) {
            rule = Rule$relative_part.parse(context);
        } else if (rulename.equalsIgnoreCase("scheme")) {
            rule = Rule$scheme.parse(context);
        } else if (rulename.equalsIgnoreCase("authority")) {
            rule = Rule$authority.parse(context);
        } else if (rulename.equalsIgnoreCase("userinfo")) {
            rule = Rule$userinfo.parse(context);
        } else if (rulename.equalsIgnoreCase("host")) {
            rule = Rule$host.parse(context);
        } else if (rulename.equalsIgnoreCase("port")) {
            rule = Rule$port.parse(context);
        } else if (rulename.equalsIgnoreCase("IP-literal")) {
            rule = Rule$IP_literal.parse(context);
        } else if (rulename.equalsIgnoreCase("IPvFuture")) {
            rule = Rule$IPvFuture.parse(context);
        } else if (rulename.equalsIgnoreCase("IPv6address")) {
            rule = Rule$IPv6address.parse(context);
        } else if (rulename.equalsIgnoreCase("h16")) {
            rule = Rule$h16.parse(context);
        } else if (rulename.equalsIgnoreCase("ls32")) {
            rule = Rule$ls32.parse(context);
        } else if (rulename.equalsIgnoreCase("IPv4address")) {
            rule = Rule$IPv4address.parse(context);
        } else if (rulename.equalsIgnoreCase("dec-octet")) {
            rule = Rule$dec_octet.parse(context);
        } else if (rulename.equalsIgnoreCase("reg-name")) {
            rule = Rule$reg_name.parse(context);
        } else if (rulename.equalsIgnoreCase("path")) {
            rule = Rule$path.parse(context);
        } else if (rulename.equalsIgnoreCase("path-abempty")) {
            rule = Rule$path_abempty.parse(context);
        } else if (rulename.equalsIgnoreCase("path-absolute")) {
            rule = Rule$path_absolute.parse(context);
        } else if (rulename.equalsIgnoreCase("path-noscheme")) {
            rule = Rule$path_noscheme.parse(context);
        } else if (rulename.equalsIgnoreCase("path-rootless")) {
            rule = Rule$path_rootless.parse(context);
        } else if (rulename.equalsIgnoreCase("path-empty")) {
            rule = Rule$path_empty.parse(context);
        } else if (rulename.equalsIgnoreCase("segment")) {
            rule = Rule$segment.parse(context);
        } else if (rulename.equalsIgnoreCase("segment-nz")) {
            rule = Rule$segment_nz.parse(context);
        } else if (rulename.equalsIgnoreCase("segment-nz-nc")) {
            rule = Rule$segment_nz_nc.parse(context);
        } else if (rulename.equalsIgnoreCase("pchar")) {
            rule = Rule$pchar.parse(context);
        } else if (rulename.equalsIgnoreCase("query")) {
            rule = Rule$query.parse(context);
        } else if (rulename.equalsIgnoreCase("fragment")) {
            rule = Rule$fragment.parse(context);
        } else if (rulename.equalsIgnoreCase("pct-encoded")) {
            rule = Rule$pct_encoded.parse(context);
        } else if (rulename.equalsIgnoreCase("unreserved")) {
            rule = Rule$unreserved.parse(context);
        } else if (rulename.equalsIgnoreCase("reserved")) {
            rule = Rule$reserved.parse(context);
        } else if (rulename.equalsIgnoreCase("gen-delims")) {
            rule = Rule$gen_delims.parse(context);
        } else if (rulename.equalsIgnoreCase("sub-delims")) {
            rule = Rule$sub_delims.parse(context);
        } else {
            throw new IllegalArgumentException("unknown rule");
        }

        if (rule == null) {
            Logger.getLogger(Parser.class.getName()).log(Level.INFO, "RULE NULL: {0}", new Object[]{context.index});
            throw new ParserException(
                    context.getMessage() != null ? context.getMessage() : "rule \"" + (!context.getErrorStack().isEmpty() ? (String) context.getErrorStack().peek() : "") + "\" failed",
                    context.text,
                    context.getErrorIndex(),
                    context.getErrorStack(),
                    context.getZcrawling());
        }

        if (context.text.length() > context.index) {
            throw new ParserException(
                    "extra data found",
                    context.text,
                    context.index,
                    new Stack<String>(),
                    context.getZcrawling());
        }

        return rule;
    }

    static private Rule parse(String rulename, InputStream in, boolean trace, ZCrawling zcrawling)
            throws IllegalArgumentException,
            IOException,
            ParserException {
        if (rulename == null) {
            throw new IllegalArgumentException("null rulename");
        }
        if (in == null) {
            throw new IllegalArgumentException("null input stream");
        }

        int ch = 0;
        StringBuffer out = new StringBuffer();
        while ((ch = in.read()) != -1) {
            out.append((char) ch);
        }

        return parse(rulename, out.toString(), trace, zcrawling);
    }

    static private Rule parse(String rulename, File file, boolean trace, ZCrawling zcrawling)
            throws IllegalArgumentException,
            IOException,
            ParserException {
        if (rulename == null) {
            throw new IllegalArgumentException("null rulename");
        }
        if (file == null) {
            throw new IllegalArgumentException("null file");
        }

        BufferedReader in = new BufferedReader(new FileReader(file));
        int ch = 0;
        StringBuffer out = new StringBuffer();
        while ((ch = in.read()) != -1) {
            out.append((char) ch);
        }

        in.close();

        return parse(rulename, out.toString(), trace, zcrawling);
    }
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
