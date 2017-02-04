package httpsvr

import java.util.Map
import java.util.logging.Logger

import org.nanohttpd.protocols.http._
import org.nanohttpd.protocols.http.request.Method
import org.nanohttpd.protocols.http.response.Response
import org.nanohttpd.util.ServerRunner

object ServerStart{

    def main(args : Array[String]) {
        val s = new Server()
        ServerRunner.run(s.getClass)
    }
}

class Server(port:Int) extends NanoHTTPD(port){
  val LOG = Logger.getLogger(this.getClass.getName)

  def this() =  {
    this(8080)
  }

  override def serve(session:IHTTPSession):Response = {
      val method:Method = session.getMethod
      val uri:String = session.getUri

      this.LOG.info(method + " '" + uri + "' ")

      var msg:String = "<html><body><h1>Hello server</h1>\n"
      val parms:Map[String,String] = session.getParms
      if(parms.get("username") == null){
          msg += "<form action='?' method='get'>\n"
          msg += "  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n"
      } else {
          msg += "<p>Hello, " + parms.get("username") + "!</p>"
      }

      msg += "</body></html>\n"

      Response.newFixedLengthResponse(msg)
  }
}
