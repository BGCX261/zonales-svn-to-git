package org.zonales.parser.parser;

/* -----------------------------------------------------------------------------
 * Rule$unidadTiempo.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.0
 * Produced : Thu May 24 11:19:56 ART 2012
 *
 * -----------------------------------------------------------------------------
 */

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zonales.metadata.ZCrawling;

final public class Rule$unidadTiempo extends Rule
{
  private Rule$unidadTiempo(String spelling, ArrayList<Rule> rules)
  {
    super(spelling, rules);
  }

  public Object accept(ZCrawling zcrawling, Visitor visitor)
  {
    return visitor.visit(zcrawling, this);
  }

  public static Rule$unidadTiempo parse(ParserContext context)
  {
    context.push("unidadTiempo");

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
            rule = Terminal$StringValue.parse(context, "minutos");
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
            rule = Terminal$StringValue.parse(context, "horas");
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
            rule = Terminal$StringValue.parse(context, "dias");
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
      rule = new Rule$unidadTiempo(context.text.substring(s0, context.index), e0);
    else
      context.index = s0;
    
    if(rule != null) {
        Logger.getLogger("UnidadTiempo").log(Level.INFO, "Recupero tiempo seteado: {0}", context.getZcrawling().getPeriodicidad());
        Logger.getLogger("UnidadTiempo").log(Level.INFO, "Valor regla: {0}", rule.spelling);
        if (rule.spelling.equals("horas"))
            context.getZcrawling().setPeriodicidad(context.getZcrawling().getPeriodicidad() * 60);
        if (rule.spelling.equals("dias"))
            context.getZcrawling().setPeriodicidad(context.getZcrawling().getPeriodicidad() * 60 * 24);
    }

    context.pop("unidadTiempo", parsed);

    return (Rule$unidadTiempo)rule;
  }
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
