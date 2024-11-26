import { Tunnel } from "./17_Tunnel";
let tunnel = new Tunnel(7);
tunnel.simulateRocks(2022);
console.log("Tower height with 2022 Rocks: ");
console.log(tunnel.getSyntheticHeight());
console.log("");
tunnel.simulateRocks(1000000000000);
console.log("Tower height with 1000000000000 Rocks: ");
console.log(tunnel.getSyntheticHeight());
