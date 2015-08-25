/* -----------------------------------------------------------------------------
 * Rule$usuarios.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.0
 * Produced : Wed Aug 03 09:32:54 ART 2011
 *
 * -----------------------------------------------------------------------------
 */
package org.zonales.parser.parser;

import java.util.ArrayList;
import org.zonales.metadata.ZCrawling;

final public class Rule$usuarios extends Rule {

    private Rule$usuarios(String spelling, ArrayList<Rule> rules) {
        super(spelling, rules);
    }

    public Object accept(ZCrawling zcrawling, Visitor visitor) {
        return visitor.visit(zcrawling, this);
    }

    public static Rule$usuarios parse(ParserContext context) {
        context.push("usuarios");

        boolean parsed = true;
        int s0 = context.index;
        ArrayList<Rule> e0 = new ArrayList<Rule>();
        Rule rule;

        parsed = false;
        if (!parsed) {
            {
                ArrayList<Rule> e1 = new ArrayList<Rule>();
                int s1 = context.index;
                parsed = true;
                if (parsed) {
                    boolean f1 = true;
                    int c1 = 0;
                    for (int i1 = 0; i1 < 1 && f1; i1++) {
                        rule = Rule$usuario.parse(context);
                        if ((f1 = rule != null)) {
                            e1.add(rule);
                            c1++;
                        }
                    }
                    parsed = c1 == 1;
                }
                if (parsed) {
                    boolean f1 = true;
                    int c1 = 0;
                    for (int i1 = 0; i1 < 1 && f1; i1++) {
                        parsed = false;
                        if (!parsed) {
                            {
                                ArrayList<Rule> e2 = new ArrayList<Rule>();
                                int s2 = context.index;
                                parsed = true;
                                if (parsed) {
                                    boolean f2 = true;
                                    int c2 = 0;
                                    for (int i2 = 0; i2 < 1 && f2; i2++) {
                                        rule = Rule$mspace.parse(context);
                                        if ((f2 = rule != null)) {
                                            e2.add(rule);
                                            c2++;
                                        }
                                    }
                                    parsed = c2 == 1;
                                }
                                if (parsed) {
                                    boolean f2 = true;
                                    int c2 = 0;
                                    for (int i2 = 0; i2 < 1 && f2; i2++) {
                                        rule = Rule$place.parse(context);
                                        if ((f2 = rule != null)) {
                                            e2.add(rule);
                                            c2++;
                                        }
                                    }
                                    parsed = c2 == 1;
                                }
                                if (parsed) {
                                    e1.addAll(e2);
                                } else {
                                    context.index = s2;
                                }
                            }
                        }
                        if (parsed) {
                            c1++;
                        }
                        f1 = parsed;
                    }
                    parsed = true;
                }
                if (parsed) {
                    boolean f1 = true;
                    int c1 = 0;
                    while (f1) {
                        parsed = false;
                        if (!parsed) {
                            {
                                ArrayList<Rule> e2 = new ArrayList<Rule>();
                                int s2 = context.index;
                                parsed = true;
                                if (parsed) {
                                    boolean f2 = true;
                                    int c2 = 0;
                                    for (int i2 = 0; i2 < 1 && f2; i2++) {
                                        rule = Rule$space.parse(context);
                                        if ((f2 = rule != null)) {
                                            e2.add(rule);
                                            c2++;
                                        }
                                    }
                                    parsed = c2 == 1;
                                }
                                if (parsed) {
                                    boolean f2 = true;
                                    int c2 = 0;
                                    for (int i2 = 0; i2 < 1 && f2; i2++) {
                                        rule = Rule$comma.parse(context);
                                        if ((f2 = rule != null)) {
                                            e2.add(rule);
                                            c2++;
                                        }
                                    }
                                    parsed = c2 == 1;
                                }
                                if (parsed) {
                                    boolean f2 = true;
                                    int c2 = 0;
                                    for (int i2 = 0; i2 < 1 && f2; i2++) {
                                        rule = Rule$space.parse(context);
                                        if ((f2 = rule != null)) {
                                            e2.add(rule);
                                            c2++;
                                        }
                                    }
                                    parsed = c2 == 1;
                                }
                                if (parsed) {
                                    boolean f2 = true;
                                    int c2 = 0;
                                    for (int i2 = 0; i2 < 1 && f2; i2++) {
                                        rule = Rule$usuario.parse(context);
                                        if ((f2 = rule != null)) {
                                            e2.add(rule);
                                            c2++;
                                        }
                                    }
                                    parsed = c2 == 1;
                                }
                                if (parsed) {
                                    boolean f2 = true;
                                    int c2 = 0;
                                    for (int i2 = 0; i2 < 1 && f2; i2++) {
                                        parsed = false;
                                        if (!parsed) {
                                            {
                                                ArrayList<Rule> e3 = new ArrayList<Rule>();
                                                int s3 = context.index;
                                                parsed = true;
                                                if (parsed) {
                                                    boolean f3 = true;
                                                    int c3 = 0;
                                                    for (int i3 = 0; i3 < 1 && f3; i3++) {
                                                        rule = Rule$place.parse(context);
                                                        if ((f3 = rule != null)) {
                                                            e3.add(rule);
                                                            c3++;
                                                        }
                                                    }
                                                    parsed = c3 == 1;
                                                }
                                                if (parsed) {
                                                    e2.addAll(e3);
                                                } else {
                                                    context.index = s3;
                                                }
                                            }
                                        }
                                        if (parsed) {
                                            c2++;
                                        }
                                        f2 = parsed;
                                    }
                                    parsed = true;
                                }
                                if (parsed) {
                                    e1.addAll(e2);
                                } else {
                                    context.index = s2;
                                }
                            }
                        }
                        if (parsed) {
                            c1++;
                        }
                        f1 = parsed;
                    }
                    parsed = true;
                }
                if (parsed) {
                    e0.addAll(e1);
                } else {
                    context.index = s1;
                }
            }
        }

        rule = null;
        if (parsed) {
            rule = new Rule$usuarios(context.text.substring(s0, context.index), e0);
        } else {
            context.index = s0;
        }

        context.pop("usuarios", parsed);

        return (Rule$usuarios) rule;
    }
}

/*
 * -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
