# java-network-programming-harold

This repo summarizes the material in _Java Network Programming by Elliotte Rusty Harold_.

[Order Java Network Programming by Harold on Amazon](https://www.amazon.com/Network-Programming-Elliotte-Rusty-Harold/dp/1449357679)
___
## Chapter 1 :: Basic Network Concepts

A _network_ is a collection of computers and other devices that can send data to and receive data from one another, more or less
in real time.

Each machine on a network is called a _node_. Most nodes are computers, but printers, routers, bridges, gateways, dumb terminals,
and Coca-Cola machines can also be nodes. Nodes that are fully functional computers are also called _hosts_. We might use Java
to interface with a Coke machine, but otherwise we'll mostly talk to other computers.

Every node has an _address_, a sequence of bytes that uniquely identifies it. The more bytes there are in each address, the more
addresses there are available and the more devices that can be connected to the network simultaneously.

Addresses are assigned differently on different kinds of networks. Ethernet addresses are attached to the physical Ethernet hardware.
Internet addresses are normally assigned to a computer by the organization responsible for it. However,
the addresses that an organization is allowed to choose for its computers are assigned by the organization's ISP (i.e., Verizon, Optimum, Spectrum).
ISPs get their IP addresses from one of four regional Internet registries, which are in turn assigned IP addresses by the
Internet Corporation for Assigned Names and Numbers (ICANN).

All modern computer networks are _packet-switched_ networks: data traveling on the network is broken into chunks called 
_packets_ and each packet is handled separately. Each packet contains information about who sent it and where it's going.
Checksums can be used to detect whether a packet was damaged in transit.

A _protocol_ is a precise set of rules defining how computers communicate: the format of address, how data is split into packets, 
and so on. The Hypertext Transfer Protocol (HTTP) defines how web browsers and servers communicate. The IEEE 802.3 standard
defines a protocol for how bits are encoded as electrical signals on a particular type of wire.

Software that sends data across a network must understand how to avoid collisions between packets, convert digital data to 
analog signals, detect and correct errors, route packets from one host to another, and more.

There are several layer models, each organized to fit the needs of a particular kind of network. This book uses the standard TCP/IP
four-layer model appropriate for the Internet. In this model, applications like Firefox and Warcraft run in the application layer and
talk only to the transport layer (TCP or UDP). The transport layer talks only to the application layer and the Internet layer (IP). The Internet
layer in turn talks only to the host-to-network (Ethernet, WiFi, LTE) layer and the transport layer, never directly to the application layer.

The host-to-network layer moves the data across the wires, fiber-optic cables, or other medium to the host-to-network layer on the
remote system, which then moves the data up the layers to the application on the remote system.

For example, when a web browser sends a request to a web server to retrieve a page, the browser is actually talking to the
transport layer on the local client machine. The transport layer breaks the request into TCP segments, adds some sequence numbers
and checksums to the data, and then passes the request to the local internet layer. The Internet layer fragments the segments
into IP datagrams of the necessary size for the local network and passes them to the host-to-network layer for transmission onto
the wire. The host-to-network layer encodes the digital data as analog signals appropriate for the particular physical medium
and sends the request out the wire where it will be read by the host-to-network layer of the remote system to which it's addressed

The host-to-network layer on the remote system decodes the analog signals into digital data, then passes the resulting IP datagrams to the server's
Internet layer. The Internet layer does some simple checks to see that the IP datagrams aren't corrupt, reassembles
them if they've been fragmented, and passes them to the server's transport layer. The server's transport layer
checks to see that all the data arrived and requests retransmission of any missing or corrupt pieces. Once the server's transport
layer has received enough contiguous, sequential datagrams, it reassembles them and writes them onto a stream read by the web server running in the server
application layer.

The server responds to the request and sends its response back down through the layers on the server system for transmission back
across the Internet and delivery to the web client. Generally speaking, we live in the application (90% of the time) and transport layer (10% of the time).
The complexity of the host-to-network layer is hidden from us; that's the point of the layer model.

To the application layer, it seems as if it is talking directly to the application layer on the other system; the network
creates a logical path between the two application layers.

The host-to-network layer defines how a particular network interface - such as an Ethernet card or a WiFi antenna - sends IP 
datagrams over its physical connection to the local network and the world.

The primary reason a Java developer needs to think about the host-to-network and physical layer is performance. For instance,
if your clients reside on fast, reliable fiber-optic connections, you will design your protocol and applications differently than
if they're on high-latency satellite connections on an oil rig in the North Sea. Whichever physical links we encounter, the APIs
we use to communicate across those networks are the same. What makes that possible is the internet layer.

The internet layer is made up of two protocols: IPv4 (32 bit addresses) and IPv6 (128 bit addresses). Although these are two very
different network protocols that do not interoperate on the same network without special gateways and/or tunneling protocols, Java
hides almost all the differences from you.

Besides, routing and addressing, the second purpose of the Internet layer is to enable different types of host-to-network layers to talk to 
each other. 

The transport layer is responsible for ensuring that packets are received in the order they were sent and that no data is lost or corrupted.
If a packet is lost, the transport layer can ask the sender to retransmit the packet.

There are two primary protocols at this level. The first, the Transmission Control Protocol (TCP) is a high-overhead protocol
that allows for retransmission of lost or corrupted data and delivery of bytes in the order they were sent. The second protocol,
The User Datagram Protocol (UDP), allows the receiver to detect corrupted packets but does not guarantee that packets are delivered
in the correct order (or at all).

UDP is often much faster than TCP. TCP is called a _reliable_ protocol; UDP is an _unreliable_ protocol.

The layer that delivers data to the user is called the _application layer_. The application layer decides what to do with the 
data after it's transferred. For example, an application protocol like HTTP (for the WWW) makes sure that your web browser displays
a graphic image as a picture, not a long stream of numbers. There is an entire alphabet soup of application layer protocols:
in addition to HTTP for the Web, there are SMTP, POP, and IMAP for email; FTP, FSP and TFTP for file transfer; NFS for file access;
Gnutella and BitTorrent for file sharing; the Session Initiation Protocol (SIP) and Skype for voice communication.

IP (Internet protocol) was developed with military sponsorship during the Cold War, and ended up with a lot of features that
the military was interested in. IP was designed to allow multiple routes between any two points and to route packets of data around
damaged routers - the entire network couldn't stop functioning if the Soviets nuked a router in Cleveland. The military also needed
to have many kinds of computers be able to talk to each other.

Every computer on an IPv4 network is identified by a 4 byte number. This is normally written in a _dotted quad_ format like 199.1.32.90,
where each of the 4 numbers is one unsigned byte ranging in value from 0 to 255. 

When data is transmitted across the network, the packet's
header includes the address of the machine for which the packet is intended (the destination address) and the address of
the machine that sent the packet (the source address).

IPv6 addresses are customarily written in eight blocks of four hexadecimal digits separated by colons such as
FEDC:BA98:7654:3210:FEDC:BA98:7654:3210. Leading zeros do not need to be written. A double colon indicates multiple zero blocks.
For example FEDC::DC:0:7076:10 is equivalent to FEDC:0000:0000:0000:00DC:0000:7076:0000.

The Domain Name System (DNS) was developed to translate hostnames that humans can remember, such as `www.oreilly.com` into numeric
Internet addresses such as 208.201.239.101.

IP addresses may change over time, so we should not write code that relies on a system having the same IP address.

Several address blocks and patterns are special. All IPv4 addresses that begin with 10., 172.16, 172.31 and 192.168 are unassigned.
They can be used on internal networks, but no host using addresses in these blocks is allowed onto the global Internet.
These _non-routable_ addresses are useful for building private networks that can't be seen on the Internet.

IPv4 addresses beginning with 127 (most commonly 127.0.0.1 aka localhost) always mean _local loopback address_. These addresses always point
to the local computer, no matter which computer you're running on. In IPv6, 0:0:0:0:0:0:0:1 is the loopback address. The address 0.0.0.0
always refers to the originating host, but may only be used as a source address, not a destination.

The IPv4 address that uses the same number for each of the 4 bytes (i.e., 255.255.255.255) is a broadcast address. Packets sent to this address
are received by all nodes on the local network, though they are not routed beyond the local network.

Each computer with an IP address has several thousand logical ports (65,535 per transport layer protocol). These are purely
abstractions in the computer's memory and do not represent anything physical. Each port can be allocated to a particular service.

We say that a web server _listens_ on port XYZ (i.e., port 80) for incoming connections.

The _Internet_ is the world's largest IP-based network. It is an amorphous group of computers in different countries on all
seven continents (Antarctica included) that talk to one another using IP protocols. Each computer on the Internet has at least one
IP address by which it can be identified. The Internet is not owned by anyone, although pieces of it are. It is not governed by anyone.
It is simply a very large collection of computers that have agreed to talk to one another in a standard way.

The most important rules on the Internet deal with the assignment of addresses to different organizations, companies and individuals.
If everyone picked the Internet addresses they wanted at random, conflicts would arise almost immediately when different computers showed
up on the Internet with the same address.

Most networks today use Network Address Translation (NAT). In NAT-based networks most nodes only have local, non-routable addresses
selected from either 10.x.x.x, 172.16.x.x to 172.31.x.x or 192.168.x.x. The routers that connect the local networks to the ISP
translate these local addresses to a much smaller set of routable addresses.

For instance, the dozen or so IP nodes in my apartment all share a single externally visible IP address. The router watches my
outgoing and incoming connections and adjusts the addresses in IP packets. For an outgoing packet, it changes the source address to the
router's external address. For an incoming packet, it changes the destination address to one of the local addresses. It is important
to remember that the external and internal addresses may not be the same.

The hardware and software that sit between the Internet (global) and the local network (local), checking all the data that comes
in or out to make sure it's kosher, is called a _firewall_. The firewall is often part of the router that connects the local network
to the broader Internet and may perform other tasks, such as network address translation. It is responsible for inspecting each packet
that passes into or out of its network interface and accepting it or rejecting it according to set of rules.

Most modern network programming is based on a client/server model. A client/server application typically stores large quantities of data
on an expensive, high-powered server or cloud of servers while most of the program logic and the user interface is handled by client software running on relatively cheap 
personal computers. In most cases, a server primarily sends data while a client primarily receives it.

A more reliable distinction is that a client initiates a conversation while a server waits for clients to start conversations with it.

Data is stored on the web server and is sent out to the clients that request it. Aside from the initial request for a page, almost
all data is transferred from the server to the client, not from the client to the server.

Not all applications fit easily into a client/server model. For instance, in networked games, it seems likely that both players
will send data back and forth roughly equally. These sorts of connections are called _peer to peer_. The telephone system is the class example
of a peer-to-peer network. Each phone can either call another phone or be called by another phone. You don't have to buy one phone
to send calls and another to receive them.

Java does not have explicit peer-to-peer communication in its core networking API.

---
## Chapter 2 :: Streams

I/O in Java is built on _streams_. Input streams read data. Output streams write data. All output streams have the same basic
methods to write data and all input streams use the same basic methods to read data. After a stream is created, you can often ignore
the details of exactly what it is you're reading or writing.

Filter streams can be chained to either an input stream or an output stream. Filters can modify the data as it's read or written -
for instance, by encrypting or compressing it - or they can simply provide additional methods for converting the data that's read
or written into other formats.

Streams are synchronous; that is, when a program (really a thread) asks a stream to read or write a piece of data, it waits 
for the data to be read or written before it does anything else.

Java's basic output clas is `java.io.OutputStream`. The class provides the fundamental methods needed to write data:
* `public abstract void write(int b) throws IOException`
* `public void write(byte[] data) throws IOException`
* `public void write(byte[] data, int offset, int length) throws IOException`
* `public void flush() throws IOException`
* `public void close() throws IOException`

Subclasses of `OutputStream` use these methods to write data onto particular media. `FileOutputStream` uses these methods to write
data into a file. A `TelnetOutputStream` uses these methods to write data onto a network connection. A `ByteArrayOutputStream`
uses these methods to write data into an expandable byte array.

Streams can also be buffered in software. This is accomplished by chaining `BufferedOutputStream` or a `BufferedWriter` to the
underlying stream. Consequently, if you are done writing data, it's important to flush the output stream.
For example, suppose you've written a 300byte request to an HTTP 1.1 server that uses HTTP Keep-Alive. You generally want to wait
for a response before sending any more data. However, if the output stream has a 1,024 byte buffer, the stream may be waiting for more
data to arrive before it sends the data out of its buffer. No more data will be written onto the stream until the server response
arrives, but the response is never going to arrive because the request hasn't been sent yet!

The `flush()` method breaks the deadlock by forcing the buffered stream to send its data even if the buffer isn't yet full.
It's important to flush your stream whether you think you need to or not. You should flush all streams immediately before you close them.
Otherwise, data left in the buffer when the stream is closed may get lost.

Finally, when you're done with a stream, close it by invoking its `close()` method. THis releases any resources associated with the stream, such
as file handles or ports.

This technique is sometimes called the _dispose pattern_:

```
// the dispose pattern
try (OutputStream out = new FileOutputStream("tmp/data.txt")) {
} catch (IOException ie) {
    System.err.println(ie.getMessage());
}
```

Java automatically invokes `close()` on any `AutoCloseable` objects declared inside the argument list of
the try block.

Java's basic input clas is `java.io.InputStream`. The class provides the fundamental methods needed to read data:
* `public abstract int read(int b) throws IOException`
* `public int read(byte[] input) throws IOException`
* `public int read(byte[] input, int offset, int length) throws IOException`
* `public long skip(long n) throws IOException`
* `public int available() throws IOException`
* `public void close() throws IOException`

The `DataInputStream` and `DataOutputStream` classes provide methods for reading and writing Java's primitive data types
and strings in a binary format. The binary formats used are primarily intended for exchanging data between two different
Java programs through a network connection, a datafile, a pipe, or some other intermediary. What a data output stream writes,
a data input stream can read.

___

## Chapter 3 :: Threads

 A _thread_ (small "t") is a separate, independent path of execution in the virtual machine. A `Thread` (big "t") is an instance of
 `java.lang.Thread` class. There is a one-to-one relationship between threads executing in the VM and `Thread` objects constructed
 by the VM.
 
To start a new thread running in the virtual machine, you construct an instance of the `Thread` class and invoke its `start()`
method:

```
Thread t = new Thread();
t.start();
```

To give a thread something to do, you either subclass the `Thread` class and override its `run()` method, or implement the `Runnable`
interface and pass the `Runnable` object to the `Thread` constructor.

You put all the work the thread does in `public void run()`. The thread starts here and stops here. When the `run()` method
completes, the thread dies. In essence, the `run()` method is to a thread what the `main()` method is to a traditional non-threaded
program.

