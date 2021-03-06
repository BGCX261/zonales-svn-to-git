package org.zonales.parser.parser;

/* -----------------------------------------------------------------------------
 * ParserContext.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.0
 * Produced : Wed Jul 20 09:15:24 ART 2011
 *
 * -----------------------------------------------------------------------------
 */

import java.util.Stack;
import org.zonales.metadata.ZCrawling;

public class ParserContext
{
  public final String text;
  public int index;
  
    public String message = null;

    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the value of message
     *
     * @param message new value of message
     */
    public void setMessage(String message) {
        this.message = message;
    }

  
  private Stack<String> callStack = new Stack<String>();
  private Stack<String> errorStack = new Stack<String>();
  private int level = 0;
  private int error = -3;
  private int start = 0;
  private ZCrawling zcrawling;

    public ZCrawling getZcrawling() {
        return zcrawling;
    }

    public void setZcrawling(ZCrawling zcrawling) {
        this.zcrawling = zcrawling;
    }

  private final boolean traceOn;

  public ParserContext(String text, boolean traceOn, ZCrawling zcrawling)
  {
    this.text = text;
    this.traceOn = traceOn;
    index = 0;
    this.zcrawling = zcrawling;
  }

  public void push(String rulename)
  {
    push(rulename, "");
  }

  public void push(String rulename, String trace)
  {
    callStack.push(rulename);
    start = index;
    if (traceOn)
    {
      System.out.println("-> " + ++level + ": " + rulename + "(" + (trace != null ? trace : "") + ")");
      System.out.println(index + ": " + text.substring(index, index + 10 > text.length() ? text.length() : index + 10).replaceAll("[^\\p{Print}]", " "));
    }
  }

  public void pop(String function, boolean result)
  {
    callStack.pop();
    if (traceOn)
    {
      System.out.println("<- " + level-- + ": " + function + "(" + (result ? "true," : "false,") + (index - start) + ")");
    }
    if (!result)
    {
      if (index > error)
      {
        error = index;
        errorStack = new Stack<String>();
        errorStack.addAll(callStack);
      }
    }
    else
    {
      if (index > error) error = -1;
    }
  }

  public Stack<String> getErrorStack()
  {
    return errorStack;
  }

  public int getErrorIndex()
  {
    return error;
  }
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
