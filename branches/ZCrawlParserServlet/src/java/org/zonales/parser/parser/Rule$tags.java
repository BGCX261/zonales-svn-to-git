/* -----------------------------------------------------------------------------
 * Rule$tags.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.0
 * Produced : Thu Aug 04 12:08:11 ART 2011
 *
 * -----------------------------------------------------------------------------
 */
package org.zonales.parser.parser;

import java.util.ArrayList;
import java.util.Arrays;
import org.zonales.metadata.ZCrawling;

final public class Rule$tags extends Rule {

    private Rule$tags(String spelling, ArrayList<Rule> rules) {
        super(spelling, rules);
    }

    public Object accept(ZCrawling zcrawling, Visitor visitor) {
        return visitor.visit(zcrawling, this);
    }

    public static Rule$tags parse(ParserContext context) {
        context.push("tags");

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
                        rule = Rule$tag.parse(context);
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
                                        rule = Rule$tag.parse(context);
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
                    e0.addAll(e1);
                } else {
                    context.index = s1;
                }
            }
        }

        rule = null;
        if (parsed) {
            rule = new Rule$tags(context.text.substring(s0, context.index), e0);
        } else {
            context.index = s0;
        }

        context.pop("tags", parsed);

        if (rule != null) {
            context.getZcrawling().setTags(Arrays.asList(rule.spelling.split(",")));
        }

        return (Rule$tags) rule;
    }
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
