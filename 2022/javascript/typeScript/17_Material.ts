export enum Material {
    Air = ".",
    Rock = "#"
}

export function fromString(input: string): Material {
    switch (input) {
        case Material.Air:
            return Material.Air;
        case Material.Rock:
            return Material.Rock;
        default:
            return Material.Air;
    }
}