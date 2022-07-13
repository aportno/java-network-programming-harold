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

