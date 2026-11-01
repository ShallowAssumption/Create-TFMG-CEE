<div align="center">
  <img src="https://cdn.modrinth.com/data/hC1xYvnS/d3a8fb2ed43776c214a33bff66837939d7123d5c_96.webp">
  <h1>Create: TFMG, CEE Edition</h1>
  <br>
  <a>Heavy Engineering & Oil For The Create Mod</a>
</div>

<br>

## What is This?

A fork of [Create: TFMG CE](https://github.com/Metallurgists-of-Create/Create-TFMG-CE), replacing TFMG's electric system with deep integration with [Create Electro Energistics](https://github.com/george8188625/Create-Electro-Energetics/). Effectivley, it turns TFMG into a CEE addon. **This will not work without CEE installed**.

>[!WARNING]
> THESE ARE BREAKING CHANGES!
>
> Expect removed content to get voided!

## FAQ

### CEE v1.2.0?
Dev builds can be found on the CEE github page. If you don't want to use preview builds, you should wait on this fork.

### Create: Power Grid compatibility?
CPG has compatiblity with regular TFMG-CE. The current implementation of TFMG-CEE does not maintain that compatibility, but that compatibility may return at a later date.

## Building from Source
Since this addon is developed in anticipation of version 1.2.0 of CEE, it compiles based on a local file rather than a maven.
The path it looks for is `../jars/electroenergetics-${cee_version}.jar`, which to be clear is a folder called `jars` outside of the project folder. Either replicate that setup, or change the path in the `build.gradle`.