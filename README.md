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

