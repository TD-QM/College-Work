# CSCI 4104 Final Project

### Disclaimer: Anything without the naming convension Phase[#]* was not done by me, but instead by my groupmates
#### "Phase 2" code done by Andy Nguyen
#### "Phase 3" code done by Kenneth Tabony
<br><br>




- For "Phase 1," the actual working algorithms are Version 1.2 and 1.5. It may be counterintuitive to think that, but those are the only versions that actually work. Other versions were testing theories that ultimately didn't amount to much.
- Version 1.2 utilizes a backtracking algorithm to generate a board. The "Testing" file is there since I was experimenting with writing the output to a file and never bothered to change it
- Version 1.5 utilizes a linear time algorithm to generate a board. While it does work, there are severe limitations on the actual boards themselves. Because of how it works, there are collections of numbers that can't be separated in wany way during the generation because if they did, they would break sudoku rules
