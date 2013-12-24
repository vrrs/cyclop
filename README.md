JavaEE Container for Http
=========================

Lightweight JavaEE Container for Http based on Netty.

Specifications
* Log Request information in a file, in a rotated manner. Information per log include: timestamp, uri, userAgent string...
* Optimize the # of request handled per second and mean time per request. 
* Use reactor pattern. Handlers are modules inside pipelines that perform a function.
