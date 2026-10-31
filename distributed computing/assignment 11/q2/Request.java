package q2;

import java.io.*;
import java.util.*;

public class Request implements Serializable {

   private long numberOfSteps;

   Request(String n) {
      numberOfSteps = Long.parseLong(n);
   }

   public long getNumberOfSteps() {
      return numberOfSteps;
   }

   public String getNumberOfStepsString() {
      return String.valueOf(numberOfSteps);
   }

}
