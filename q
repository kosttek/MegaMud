* [31m7c984d4[m -[33m (origin/master, origin/HEAD)[m Moved apple from B2 to C7. [32m(7 hours ago)[m
* [31m4b3c69b[m -[33m[m Added eastern part of the cave. [32m(7 hours ago)[m
* [31me2f8bcc[m -[33m[m Added western corridor. [32m(8 hours ago)[m
* [31mf4dd031[m -[33m[m Refactoring. [32m(8 hours ago)[m
* [31mb29d612[m -[33m[m Fixed NullPointerException on exit. [32m(8 hours ago)[m
* [31m1c7c85a[m -[33m[m Renamed SampleBot to Chochlik. Added CaveInitializer. Refactored DefaultModule. [32m(9 hours ago)[m
* [31m838c095[m -[33m[m Removed debugs. [32m(20 hours ago)[m
*   [31md3a432f[m -[33m[m Merge branch 'master' of https://github.com/kosttek/MegaMud [32m(20 hours ago)[m
|\  
| * [31m088447d[m -[33m (HEAD, master)[m fixed samplebot plus item as beahviourholder [32m(21 hours ago)[m
| * [31mebfb0e1[m -[33m[m Removed *orig files and updated .gitignore. [32m(22 hours ago)[m
| * [31m6d63604[m -[33m[m Fixed test. [32m(22 hours ago)[m
* | [31mec4b06f[m -[33m[m Added player+item modifiers synced with database. [32m(20 hours ago)[m
|/  
* [31m2b9401e[m -[33m[m fighting, beahviorholder [32m(2 days ago)[m
* [31ma88292b[m -[33m[m after merge [32m(2 days ago)[m
*   [31m0ecd018[m -[33m[m after merge [32m(2 days ago)[m
|\  
| *   [31m9957f59[m -[33m[m Merge branch 'master' of https://github.com/kosttek/MegaMud [32m(2 days ago)[m
| |\  
| * | [31m08a5e84[m -[33m[m Modules loaded from database on boot. DefaultModule now loads partially from database. [32m(2 days ago)[m
* | | [31mfd4dc4c[m -[33m[m equip items and mock attributes [32m(2 days ago)[m
| |/  
|/|   
* | [31m37f9986[m -[33m[m Added initialization of the strength attribute. [32m(2 days ago)[m
|/  
| * [31m04a4c67[m -[33m (origin/client)[m klient od jasuwienasa i gila [32m(22 hours ago)[m
|/  
* [31maec4378[m -[33m[m Fixed test for CreatureItem. CreatureItem can have null PlayerCreature. [32m(9 days ago)[m
*   [31mc2ca52c[m -[33m[m Merge branch 'master' of https://github.com/kosttek/MegaMud [32m(10 days ago)[m
|\  
| * [31me9dccdf[m -[33m[m Added methods to make connections between Locations easier to work with. [32m(10 days ago)[m
| * [31mb67782a[m -[33m[m Added Portal - a one way connection between locations. [32m(10 days ago)[m
| * [31mc1ba929[m -[33m[m Added Location entity. [32m(10 days ago)[m
* | [31m9049b8f[m -[33m[m Fixes. [32m(10 days ago)[m
|/  
* [31m6fcdefb[m -[33m[m Bugfix (refreshing PlayerCreature after creating new one during registration). [32m(10 days ago)[m
*   [31mf783a3d[m -[33m[m Merge branch 'master' of https://github.com/kosttek/MegaMud [32m(10 days ago)[m
|\  
* | [31m4a668d9[m -[33m[m Unused packages. [32m(10 days ago)[m
* | [31m443a924[m -[33m[m Introduced database modules - locations and NPCs are loaded from database. [32m(10 days ago)[m
* | [31m3b75583[m -[33m[m Introduced database modules, where locations' and NPCs' configuration is loaded from database. [32m(10 days ago)[m
| | *   [31mf70577c[m -[33m (origin/diakon-modules)[m Merged with master. [32m(2 weeks ago)[m
| | |\  
| |/ /  
|/| /   
| |/    
| * [31m45f3289[m -[33m[m Merged entity Item with class Item. [32m(2 weeks ago)[m
| * [31m8bed894[m -[33m[m Added CreatureAttribute. [32m(2 weeks ago)[m
| * [31md1e5477[m -[33m[m Added ItemAttribute. [32m(2 weeks ago)[m
| * [31me4d65e2[m -[33m[m Added Attribute. [32m(2 weeks ago)[m
| * [31mc7ca708[m -[33m[m Added CreatureItem. [32m(3 weeks ago)[m
| * [31m4cdf99e[m -[33m[m Added Item entity. (Requires integrating with existing Item) [32m(3 weeks ago)[m
| * [31m0044ef2[m -[33m[m Added Profession. [32m(3 weeks ago)[m
| * [31mbc811f9[m -[33m[m Added PlayerCreature and its relation to Player. [32m(3 weeks ago)[m
| * [31m0aa2904[m -[33m[m Removed .class files. [32m(3 weeks ago)[m
* | [31m5a157ac[m -[33m[m Introduced basic modules. Introduced default module (old locations, npcs, items) and another example self-destroying module (npc). [32m(3 weeks ago)[m
|/  
*   [31mc4e971a[m -[33m[m Merge branch 'master' of https://github.com/kosttek/MegaMud [32m(3 weeks ago)[m
|\  
| * [31m7105109[m -[33m[m Added priviledges field to the Player class. [32m(3 weeks ago)[m
| * [31m2020a50[m -[33m[m Renamed Account to Player and added password hashing. [32m(3 weeks ago)[m
* | [31me08b47d[m -[33m[m Introduced item system - take item from location, drop to it, give to other creature, magically appearing/disappearing items. Introduced a simple quest using NPCs and items (grab an cyclicly appearing apple from other room to a bot). [32m(3 weeks ago)[m
|/  
* [31m71ac0cf[m -[33m[m Added login and registration functionality. [32m(3 weeks ago)[m
* [31m43ab004[m -[33m[m Added DbManager and split Account into Account and AccountBase. [32m(3 weeks ago)[m
* [31ma7bc2d5[m -[33m[m Added CR (\r) for Windows compatibility. [32m(3 weeks ago)[m
* [31md50dd0a[m -[33m[m Added various comments. [32m(3 weeks ago)[m
*   [31me7d5594[m -[33m[m Merge branch 'diakon' [32m(3 weeks ago)[m
|\  
| * [31m98d81d4[m -[33m (origin/diakon)[m Try 3: reverted commands system to key->commands list. [32m(3 weeks ago)[m
| * [31m93465ce[m -[33m[m Introduced creature's modifiers and a sample NPC (not mine idea [; ). Small refactor. [32m(4 weeks ago)[m
| * [31m2a07400[m -[33m[m Rewrite of command system. Introduced a controller (brain for NPC or bridge for client socket). [32m(4 weeks ago)[m
* |   [31m7e99ef5[m -[33m[m Merge branch 'master' of git@github.com:kosttek/MegaMud.git [32m(4 weeks ago)[m
|\ \  
| * | [31mb07c5db[m -[33m[m Added DB basics - example test case for the Account table. [32m(4 weeks ago)[m
| |/  
* | [31m8a4464d[m -[33m[m kostek gitignore [32m(4 weeks ago)[m
* | [31m3e67e50[m -[33m[m kick the chochlik [32m(4 weeks ago)[m
|/  
* [31m3f42245[m -[33m[m project files [32m(5 weeks ago)[m
* [31m6e33b07[m -[33m[m EventManager [32m(5 weeks ago)[m
* [31m5862c14[m -[33m[m refactoring [32m(5 weeks ago)[m
* [31m18a689e[m -[33m[m changed command logic [32m(5 weeks ago)[m
* [31m7b82f61[m -[33m[m sec2 commit [32m(6 weeks ago)[m
* [31m8acf868[m -[33m[m first commit [32m(6 weeks ago)[m