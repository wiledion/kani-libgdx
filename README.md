Kani
-------------------------------------------------------------------
Kani is a 2D game written with the framework LibGDX in Java. It 
wants to promote African culture and history. The gameplay is 
inspired from hack and slash and RPG games.

Advancement
-------------------------------------------------------------------
The project was firstly developed with the library Slick2d which is 
not still supported by his developers. It has been rewritten with 
LibGDX, and some features has been added to allow a separated level
designing.
Data for maps and levels can be loaded from a file.

Only the desktop version is developed.


How to edit a Map file .kni
-------------------------------------------------------------------
A map file is organised as follows :

```
mapheight = XX   	// The height of the map in number of tile
mapwidth = XX		// The width of the map in number of tile
tileheight = XX		// The height of a tile
tilewidth = XX		// The width of a tile
nblayer = XX			// The number of layer


// For every layer, 1 line contains the name of the layer, and the 
// others contain the ID of the tile corresponding, placed as on 
// the map. The tile ID is composed of three digits/characters. A 
// number corresponds to the order they appear in the tilesheets,
// from the top left to the bottom right, and the order of the 
// files tilesheets. "___" is used for a blank tile, and "aaa" is
// a special ID which generates a tree on the map. Others shortcuts
// can be added to facilitate game mapping.

layer ground
001 001 001 001 001 001
001 001 001 001 002 001
001 001 001 001 001 003
003 003 003 003 003 003
001 001 001 001 001 003

// Then we can define a property for the different tiles. A first 
// line contains the properties and the values to assign. The next
// line contains all the tile ID who will be affected by this 
// property.

blocked = true
002 003

An example is available in "android/assets/data/maps"
```

How to edit a Level file .kni
-------------------------------------------------------------------
Several parameters can be defined for the level :

```
name_level // The name of the level
intro_msg // The introduction message, printed with the class MessageBox
end_msg 	// The message of the end, in case of success of the level
dead_msg 	// The message in case of death of the player
current_map = // The path of the map file
zones = // Define a specific region in the map -- Useless
ennemis  	// Defines the enemies present in the map, is written "x-y"
		// with x = the race of the monster : 	1 - Statue
		//					2 - Bird
		// y = the number of enemies of the specified race
```
			
An example is available in android/assets/data/maps


Media resources
-------------------------------------------------------------------
Sprites are extracted from The Legend of Zelda: A Link to the Past 
Super NES) and Pokémon Emerald (Game Boy Advance). 
Thanks to Wio for the sprite of Kani and the statue.


License
-------------------------------------------------------------------
Copyright by Bonzi Wilédio, 2012, Licensed under the BSD New 
license.


