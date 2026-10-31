package q2;

import java.io.*;
import java.util.*;

public class Reply implements Serializable {

  private String serverReply;

  public Reply(String serverReply) {
    this.serverReply = serverReply;
  }

  public String getServerReply() {
    return serverReply;
  }

}
