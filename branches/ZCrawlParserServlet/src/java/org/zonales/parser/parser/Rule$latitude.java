/* -----------------------------------------------------------------------------
 * Rule$latitude.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.0
 * Produced : Mon Sep 26 09:49:50 ART 2011
 *
 * -----------------------------------------------------------------------------
 */

package org.zonales.parser.parser;

import java.util.ArrayList;
import org.zonales.metadata.ZCrawling;

final public class Rule$latitude extends Rule
{
  private Rule$latitude(String spelling, ArrayList<Rule> rules)
  {
    super(spelling, rules);
  }

  public Object accept(ZCrawling zcrawling, Visitor visitor)
  {
    return visitor.visit(zcrawling, this);
  }

  public static Rule$latitude parse(ParserContext context)
  {
    context.push("latitude");

    boolean parsed = true;
    int s0 = context.index;
    ArrayList<Rule> e0 = new ArrayList<Rule>();
    Rule rule;

    parsed = false;
    if (!parsed)
    {
      {
        ArrayList<Rule> e1 = new ArrayList<Rule>();
        int s1 = context.index;
        parsed = true;
        if (parsed)
        {
          boolean f1 = true;
          int c1 = 0;
          for (int i1 = 0; i1 < 1 && f1; i1++)
          {
            rule = Rule$num.parse(context);
            if ((f1 = rule != null))
            {
              e1.add(rule);
              c1++;
            }
          }
          parsed = c1 == 1;
        }
        if (parsed)
          e0.addAll(e1);
        else
          context.index = s1;
      }
    }

    rule = null;
    if (parsed)
      rule = new Rule$latitude(context.text.substring(s0, context.index), e0);
    else
      context.index = s0;

    context.pop("latitude", parsed);

    return (Rule$latitude)rule;
  }
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
