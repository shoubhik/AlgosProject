package com.ncsu.project

public class InputGenerator{
// upto before Network monitoring and debugging:
//url: http://tools.ietf.org/html/rfc826
    public static void main(String[] args) {

def input = """The world is a jungle in general, and the networking game
contributes many animals.  At nearly every layer of a network
architecture there are several potential protocols that could be
used.  For example, at a high level, there is TELNET and SUPDUP
for remote login.  Somewhere below that there is a reliable byte
stream protocol, which might be CHAOS protocol, DOD TCP, Xerox
BSP or DECnet.  Even closer to the hardware is the logical
transport layer, which might be CHAOS, DOD Internet, Xerox PUP,
or DECnet.  The 10Mbit Ethernet allows all of these protocols
(and more) to coexist on a single cable by means of a type field
in the Ethernet packet header.  However, the 10Mbit Ethernet
requires 48.bit addresses on the physical cable, yet most
protocol addresses are not 48.bits long, nor do they necessarily
have any relationship to the 48.bit Ethernet address of the
hardware.  For example, CHAOS addresses are 16.bits, DOD Internet
addresses are 32.bits, and Xerox PUP addresses are 8.bits.  A
protocol is needed to dynamically distribute the correspondences
between a <protocol, address> pair and a 48.bit Ethernet address.
Motivation:
Use of the 10Mbit Ethernet is increasing as more manufacturers
supply interfaces that conform to the specification published by
DEC, Intel and Xerox.  With this increasing availability, more
and more software is being written for these interfaces.  There
are two alternatives: (1) Every implementor invents his/her own
method to do some form of address resolution, or (2) every
implementor uses a standard so that his/her code can be
distributed to other systems without need for modification.  This
proposal attempts to set the standard.
As a packet is sent down through the network layers, routing
determines the protocol address of the next hop for the packet
and on which piece of hardware it expects to find the station
with the immediate target protocol address.  In the case of the
10Mbit Ethernet, address resolution is needed and some lower
layer (probably the hardware driver) must consult the Address
Resolution module (perhaps implemented in the Ethernet support
module) to convert the <protocol type, target protocol address>
pair to a 48.bit Ethernet address.  The Address Resolution module
tries to find this pair in a table.  If it finds the pair, it
gives the corresponding 48.bit Ethernet address back to the
caller (hardware driver) which then transmits the packet.  If it
does not, it probably informs the caller that it is throwing the
packet away (on the assumption the packet will be retransmitted
by a higher network layer), and generates an Ethernet packet with
a type field of ether_typeADDRESS_RESOLUTION.  The Address
Resolution module then sets the arhrd field to
ares_hrdEthernet, arpro to the protocol type that is being
resolved, arhln to 6 (the number of bytes in a 48.bit Ethernet
address), arpln to the length of an address in that protocol,
arop to ares_opREQUEST, arsha with the 48.bit ethernet address
of itself, arspa with the protocol address of itself, and artpa
with the protocol address of the machine that is trying to be
accessed.  It does not set artha to anything in particular,
because it is this value that it is trying to determine.  It
could set artha to the broadcast address for the hardware (all
ones in the case of the 10Mbit Ethernet) if that makes it
convenient for some aspect of the implementation.  It then causes
this packet to be broadcast to all stations on the Ethernet cable
originally determined by the routing mechanism.
Periodic broadcasting is definitely not desired.  Imagine 100
workstations on a single Ethernet, each broadcasting address
resolution information once per 10 minutes (as one possible set
of parameters).  This is one packet every 6 seconds.  This is
almost reasonable, but what use is it?  The workstations aren't
generally going to be talking to each other (and therefore have
100 useless entries in a table); they will be mainly talking to a
mainframe, file server or bridge, but only to a small number of
other workstations (for interactive conversations, for example).
The protocol described in this paper distributes information as
it is needed, and only once (probably) per boot of a machine.

This format does not allow for more than one resolution to be
done in the same packet.  This is for simplicity.  If things were
multiplexed the packet format would be considerably harder to
digest, and much of the information could be gratuitous.  Think
of a bridge that talks four protocols telling a workstation all
four protocol addresses, three of which the workstation will
probably never use.

This format allows the packet buffer to be reused if a reply is
generated; a reply has the same length as a request, and several
of the fields are the same.

The value of the hardware field (arhrd) is taken from a list for
this purpose.  Currently the only defined value is for the 10Mbit
Ethernet (ares_hrdEthernet = 1).  There has been talk of using
this protocol for Packet Radio Networks as well, and this will
require another value as will other future hardware mediums that
wish to use this protocol.

For the 10Mbit Ethernet, the value in the protocol field (arpro)
is taken from the set ether_type.  This is a natural reuse of
the assigned protocol types.  Combining this with the opcode
(arop) would effectively halve the number of protocols that can
be resolved under this protocol and would make a monitor/debugger
more complex (see Network Monitoring and Debugging below).  It is
hoped that we will never see 32768 protocols, but Murphy made
some laws which don't allow us to make this assumption.

In theory, the length fields (arhln and arpln) are redundant,
since the length of a protocol address should be determined by
the hardware type (found in arhrd) and the protocol type (found
in arpro).  It is included for optional consistency checking,
and for network monitoring and debugging (see below).

The opcode is to determine if this is a request (which may cause
a reply) or a reply to a previous request.  16 bits for this is
overkill, but a flag (field) is needed.

The sender hardware address and sender protocol address are
absolutely necessary.  It is these fields that get put in a
translation table.
The target protocol address is necessary in the request form of
the packet so that a machine can determine whether or not to
enter the sender information in a table or to send a reply.  It
is not necessarily needed in the reply form if one assumes a
reply is only provoked by a request.  It is included for
completeness, network monitoring, and to simplify the suggested
processing algorithm described above (which does not look at the
opcode until AFTER putting the sender information in a table).

The target hardware address is included for completeness and
network monitoring.  It has no meaning in the request form, since
it is this number that the machine is requesting.  Its meaning in
the reply form is the address of the machine making the request.
In some implementations (which do not get to look at the 14.byte
ethernet header, for example) this may save some register
shuffling or stack space by sending this field to the hardware
driver as the hardware destination address of the packet.

There are no padding bytes between addresses.  The packet data
should be viewed as a byte stream in which only 3 byte pairs are
defined to be words (arhrd, arpro and arop) which are sent
most significant byte first (Ethernet/PDP-10 byte style)."""

println System.getProperty("user.dir")
def sizes  = [150,200,250,300,500]
sizes.each{
    def inputFile = new File("input1_${it}mb.txt")
    while(inputFile.length()/(1024*1024) < it)
        inputFile << input
    println "input1_${it}mb.txt written"
}

  }

}