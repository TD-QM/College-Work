from typing import List

class Cell:
    def __init__(self, current, row, column, found, value, possible_numbers):
        self.current = current
        self.row = row
        self.column = column
        self.found = found
        self.value = value
        self.possible_numbers = possible_numbers

def is_over(cell_sudoku):
    for i in range(9):
        for j in range(9):
            if not cell_sudoku[i][j].found:
                return False
    return True



def single_cell_destroyer(cell_sudoku):
    for i in range(9):
        for j in range(9):
            if cell_sudoku[i][j].possible_numbers and len(cell_sudoku[i][j].possible_numbers) == 1:
                cell_sudoku[i][j].found = True
                cell_sudoku[i][j].value = cell_sudoku[i][j].possible_numbers[0]
                cell_sudoku[i][j].possible_numbers.clear()

    return cell_sudoku

def eliminate(cell_sudoku):
    for row in range(9):
        for col in range(9):
            if not cell_sudoku[row][col].found:
                already_have_nums = set()

                for r in range(9):
                    if cell_sudoku[row][r].found:
                        already_have_nums.add(cell_sudoku[row][r].value)

                for c in range(9):
                    if cell_sudoku[c][col].found:
                        already_have_nums.add(cell_sudoku[c][col].value)

                r, c = row - row % 3, col - col % 3

                for i in range(r, r + 3):
                    for j in range(c, c + 3):
                        if cell_sudoku[i][j].found:
                            already_have_nums.add(cell_sudoku[i][j].value)

                for num in already_have_nums:
                    if num in cell_sudoku[row][col].possible_numbers:
                        cell_sudoku[row][col].possible_numbers.remove(num)

    return cell_sudoku

def only_choice_box(cell_sudoku):
    for row in range(9):
        for col in range(9):
            if not cell_sudoku[row][col].found:
                cell_sudoku[row][col].current = True

                for num in cell_sudoku[row][col].possible_numbers.copy():
                    r, c = row - row % 3, col - col % 3
                    should_continue = True

                    for a in range(r, r + 3):
                        for b in range(c, c + 3):
                            if should_continue:
                                if not cell_sudoku[a][b].found and not cell_sudoku[a][b].current and cell_sudoku[a][b].possible_numbers and \
                                        num in cell_sudoku[a][b].possible_numbers:
                                    should_continue = False
                                    break

                                if a == r + 2 and b == c + 2:
                                    cell_sudoku[row][col].current = False
                                    cell_sudoku[row][col].found = True
                                    cell_sudoku[row][col].value = num
                                    cell_sudoku[row][col].possible_numbers.clear()

    return cell_sudoku

def only_choice_col(cell_sudoku):
    for row in range(9):
        for col in range(9):
            if not cell_sudoku[row][col].found:
                cell_sudoku[row][col].current = True

                for i in range(len(cell_sudoku[row][col].possible_numbers)):
                    should_continue = True

                    for c in range(9):
                        if cell_sudoku[c][col].found or cell_sudoku[c][col].current or \
                                cell_sudoku[c][col].possible_numbers.count(cell_sudoku[row][col].possible_numbers[i]) == 0:
                            continue

                        if c == 8:
                            cell_sudoku[row][col].current = False
                            cell_sudoku[row][col].found = True
                            cell_sudoku[row][col].value = cell_sudoku[row][col].possible_numbers[i]
                            cell_sudoku[row][col].possible_numbers.clear()
                            should_continue = False

                    if not should_continue:
                        break

                cell_sudoku[row][col].current = False

    return cell_sudoku

def only_choice_row(cell_sudoku):
    for row in range(9):
        for col in range(9):
            if not cell_sudoku[row][col].found:
                cell_sudoku[row][col].current = True

                for i in range(len(cell_sudoku[row][col].possible_numbers)):
                    should_continue = True

                    for r in range(9):
                        if cell_sudoku[row][r].found or cell_sudoku[row][r].current or \
                                cell_sudoku[row][r].possible_numbers.count(cell_sudoku[row][col].possible_numbers[i]) == 0:
                            continue

                        if r == 8:
                            cell_sudoku[row][col].current = False
                            cell_sudoku[row][col].found = True
                            cell_sudoku[row][col].value = cell_sudoku[row][col].possible_numbers[i]
                            cell_sudoku[row][col].possible_numbers.clear()
                            should_continue = False

                    if not should_continue:
                        break

                cell_sudoku[row][col].current = False

    return cell_sudoku

def check_sudoku_row(cell_sudoku):
    for row in range(9):
        number_list = []

        for i in range(9):
            if cell_sudoku[row][i].value in number_list:
                return False

            number_list.append(cell_sudoku[row][i].value)

    return True

def check_sudoku_column(cell_sudoku):
    for col in range(9):
        number_list = []

        for i in range(9):
            if cell_sudoku[i][col].value in number_list:
                return False

            number_list.append(cell_sudoku[i][col].value)

    return True

def check_sudoku(cell_sudoku):
    return check_sudoku_row(cell_sudoku) and check_sudoku_column(cell_sudoku)

def display_sudoku(cell_sudoku):
    print("---------------------------------------------")

    for i in range(9):
        for j in range(9):
            print(f" {cell_sudoku[i][j].value}", end='')

        print()

    print("---------------------------------------------")
    print()

def display_possible_number(cell_sudoku):
    print("---------------------------------------------")

    for i in range(9):
        for j in range(9):
            print(f"{cell_sudoku[i][j].possible_numbers} ", end='')

        print()

    print("---------------------------------------------")

if __name__ == "__main__":
    first_sudoku = [
        [5, 0, 1, 0, 0, 0, 6, 0, 4],
        [0, 9, 0, 3, 0, 6, 0, 5, 0],
        [0, 0, 0, 0, 9, 0, 0, 0, 0],
        [4, 0, 0, 0, 0, 0, 0, 0, 9],
        [0, 0, 0, 1, 0, 9, 0, 0, 0],
        [7, 0, 0, 0, 0, 0, 0, 0, 6],
        [0, 0, 0, 0, 2, 0, 0, 0, 0],
        [0, 8, 0, 5, 0, 7, 0, 6, 0],
        [1, 0, 3, 0, 0, 0, 7, 0, 2]
    ]
    
    first_sudoku = [
        [8, 5, 3,  0, 0, 0,  0, 0, 0],
        [2, 0, 0,  7, 5, 4,  0, 0, 3],
        [0, 0, 9,  0, 0, 3,  0, 0, 6],
    
        [0, 0, 0,  9, 8, 6,  0, 0, 7],
        [0, 0, 6,  0, 0, 5,  0, 1, 0],
        [4, 3, 0,  2, 0, 0,  0, 0, 0],
    
        [0, 0, 0,  1, 0, 0,  4, 9, 5],
        [0, 0, 5,  0, 7, 0,  2, 0, 0],
        [1, 2, 0,  0, 0, 0,  0, 0, 8]
    ]

    cell_sudoku = [[Cell(False, i, j, False, 0, []) for j in range(9)] for i in range(9)]

    for row in range(9):
        for col in range(9):
            if first_sudoku[row][col] == 0:
                numbers = list(range(1, 10))
                temp = Cell(False, row, col, False, 0, numbers)
                cell_sudoku[row][col] = temp
            else:
                numbers = []
                temp = Cell(False, row, col, True, first_sudoku[row][col], numbers)
                cell_sudoku[row][col] = temp

    row = 0
    display_sudoku(cell_sudoku)

    while not is_over(cell_sudoku):
        row += 1
        eliminate(cell_sudoku)
        single_cell_destroyer(cell_sudoku)
        eliminate(cell_sudoku)
        only_choice_row(cell_sudoku)
        eliminate(cell_sudoku)
        single_cell_destroyer(cell_sudoku)
        eliminate(cell_sudoku)
        only_choice_col(cell_sudoku)
        eliminate(cell_sudoku)
        single_cell_destroyer(cell_sudoku)
        eliminate(cell_sudoku)
        only_choice_box(cell_sudoku)
        eliminate(cell_sudoku)
        single_cell_destroyer(cell_sudoku)
        eliminate(cell_sudoku)

    check_sudoku(cell_sudoku)
    if check_sudoku(cell_sudoku):
        print("\n\nSUDOKU IS CORRECTLY SOLVED!!!")
    else:
        print("\n\nSUDOKU IS NOT CORRECTLY SOLVED!!!")

    display_sudoku(cell_sudoku)