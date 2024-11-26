document.getElementById("submit").onclick = () => {
    let input = document.getElementById("input").value;
    let packetManager = new PacketManager(input);
    document.getElementById("output").value = "Sum of indexes: " + packetManager.getSumOfIndexes();
    let packetSorter = new PacketSorter(input);
    packetSorter.sortPackets();
    document.getElementById("output").value += "\nDecoder Key: " + packetSorter.getDecoderKey();
};
class Packet {
    content;
    key;
    constructor(input, key = "0") {
        this.content = Packet.getObjectFromInput(input);
        this.key = key.toString();
    }
    static getObjectFromInput(input) {
        let final = [];
        input = input.slice(1).slice(0, -1);
        while (input.length > 0) {
            if (input[0] == "[") {
                final.push(Packet.getObjectFromInput(input.slice(0, Packet.getIndexOfArrayClose(input) + 1)));
                input = input.slice(Packet.getIndexOfArrayClose(input) + 2);
            }
            if (Number.isInteger(Number.parseInt(input[0]))) {
                let number = input.includes(",") ? input.slice(0, input.indexOf(",")) : input;
                final.push(Number.parseInt(number));
                input = input.slice(number.length + 1);
            }
        }
        return final;
    }
    static getIndexOfArrayClose(input) {
        let depth = 0;
        let index = 0;
        for (let char of input) {
            if (char == "[")
                depth++;
            if (char == "]")
                depth--;
            if (char == "]" && depth == 0)
                return index;
            index++;
        }
        return index - 1;
    }
    static rightOrder(leftPacket, rightPacket) {
        return Packet.rightOrderArray(leftPacket.content, rightPacket.content);
    }
    static rightOrderArray(leftPacket, rightPacket) {
        if (rightPacket.length == 0 && leftPacket.length > 0)
            return false;
        for (let i = 0; i < rightPacket.length; i++) {
            if (leftPacket[i] == undefined)
                return true;
            if (typeof leftPacket[i] == "number" && typeof rightPacket[i] == "number") {
                if (leftPacket[i] != rightPacket[i])
                    return leftPacket[i] < rightPacket[i];
            }
            if (leftPacket[i] instanceof Array || rightPacket[i] instanceof Array) {
                let result = Packet.rightOrderArray(leftPacket[i] instanceof Array ? leftPacket[i] : [leftPacket[i]], rightPacket[i] instanceof Array ? rightPacket[i] : [rightPacket[i]]);
                if (result != undefined)
                    return result;
            }
            if (rightPacket[i + 1] == undefined && leftPacket[i + 1] != undefined)
                return false;
        }
    }
}
class PacketManager {
    packets = [];
    constructor(input) {
        input.split("\n\n").forEach((packetPair, index) => {
            this.packets.push(new PacketPair(packetPair.split("\n")[0], packetPair.split("\n")[1], index + 1));
        });
    }
    getSumOfIndexes() {
        return this.packets.reduce((acc, cur) => cur.getCorrectOrder() ? acc + cur.index : acc, 0);
    }
}
class PacketPair {
    leftPacket;
    rightPacket;
    index;
    constructor(leftPacket, rightPacket, index) {
        this.leftPacket = new Packet(leftPacket);
        this.rightPacket = new Packet(rightPacket);
        this.index = index;
    }
    getCorrectOrder() {
        return Packet.rightOrder(this.leftPacket, this.rightPacket);
    }
}
class PacketSorter {
    packets = [];
    constructor(input) {
        for (let row of input.split("\n"))
            if (row != "")
                this.packets.push(new Packet(row));
        this.packets.push(new Packet("[[2]]", 1));
        this.packets.push(new Packet("[[6]]", 2));
    }
    sortPackets() {
        this.packets.sort((a, b) => {
            if (Packet.rightOrder(a, b))
                return -1;
            return 1;
        });
    }
    getDecoderKey() {
        return this.findIndexOf(1) * this.findIndexOf(2);
    }
    findIndexOf(index) {
        for (let i = 0; i < this.packets.length; i++) {
            if (this.packets[i].key == index.toString())
                return i + 1;
        }
        return undefined;
    }
}
export {};
