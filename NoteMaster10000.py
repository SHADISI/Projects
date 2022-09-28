"""
NoteMaster 10000
----------------

The NoteMaster 10000 is a program that allows the user to create note a dictionary of notes filed under keywords which is saved
to an external text.dat file. The user can also search by key or by text, and delete all instances of a note across keys,
or a key, including all the notes it holds. The user can also use the combobulator to find the common (uncommon) words between
two notes - intended as a stimulation to making conceptual connections.

"""
# Import modules
import pickle
import random

# Import constants

# Used by the combobulator to scrub notes of punctuation before comparing them for shared words
PUNCT = ['.', ',', '?', '!', '\'', '"','“', '-',
'(', ')', '[', ']', '{', '}', '@',
'#', '$', '%', '^', '&', '*', '/',
'\\', '|', '<', '>', ':', ';', ' ', '[',']','']

# Used by the combobulator to scrub notes of common words before comparing them for shared words
COMMS = ['the', 'be', 'to', 'of', 'and', 'a', 'in', 'that', 'have', 'I', 'it', 'for',          
         'not', 'on', 'with', 'he', 'as', 'you', 'do', 'at', 'this', 'but', 'his', 'by',
         'from', 'they', 'we', 'say', 'her', 'she', 'or', 'an', 'will', 'my', 'one',
         'all', 'would', 'there', 'their', 'what', 'so', 'up', 'out', 'if', 'about',
         'who', 'get', 'which', 'go', 'me', 'when', 'make', 'can', 'like', 'time',
         'no', 'just', 'him', 'know', 'take', 'people', 'into', 'year', 'your', 'are',
         'good', 'some', 'could', 'them', 'see', 'other', 'than', 'then', 'now','if',
         'look', 'only', 'come', 'its', 'over', 'think', 'also', 'back', 'after',
         'use', 'two', 'how', 'our', 'work', 'first', 'well', 'way', 'even', 'had',
         'new', 'want', 'because', 'any', 'these', 'give', 'day', 'most', 'us','where','is']

# The number of notes the combobulator compares
COMBOBULATOR_NUM = 2

def main():
    # main displays the title, then calls the menu, and recieving the users choice
    # calls the corresponding function
    
    display_title()

    # Set menu return value
    logoff = 'n'
    # Set menu return sentinel
    while logoff == 'n':

        # Call get_menu to obtain user choice
        user_choice = get_menu()

        # Call function corresponding to user choice

        # If 1, call new note to create a new note for the file
        if user_choice == '1':
            new_note()
        # elif 2, call search notes to search by text, key, or randomly
        # also offers options to delete notes and keys from the file
        elif user_choice == '2':
            note_search()
        # elif 3, call combobulator to analyze two random notes
        # for 'potential points of conceptual convergence'
        elif user_choice == '3':
            combobulator()
        #elif 4, call program exit
        elif user_choice == '4':
            print('Thank you for using the NoteMaster 10000!')
            logoff = 'y'

def display_title():
    # displays the program title
    print('-' * 30)
    print('NoteMaster 10000')
    print('-' * 30)
    print()

def get_notes():
    # Opens dat file containing notes dictionary in read mode, unpickles the notes dictionary,
    # and sends the dictionary to the calling function

    # Set end of file indicator
    end_of_file = False

    
    try:
        # open notes file for binary reading
        infile = open('notes.dat', 'rb')
    # If file not found, create it
    except FileNotFoundError:
        create_notes_file()
        print('There was an error locating the file. A new dictionary and file have been created.')
        infile = open('notes.dat', 'rb')

    # Read to the end of the file
    while not end_of_file:
        try:
            # Unpickle notes dictionary
            notes = pickle.load(infile)
        # Terminate process
        except EOFError:
            end_of_file = True

    # Return notes to calling function
    return notes

    # Close the file
    try:
        infile.close()
    # Warn error
    except IOError:
        Print('There was an error closing the file')

def create_notes_file():
    # When the notes.dat file is not found
    # create the file and put an empty notes dictionary in it

        try:
            # Create a file called notes.dat
            create_file = open('notes.dat', 'wb')
        # Warn error
        except IOError:
            ('There was an error. The file could not be created.')

        # Create empty notes dictionary
        notes = {}

        # Put empty notes dictionary in file
        pickle.dump(notes, create_file)

        try:
            # Close the file
            create_file.close()
        # Warn error
        except IOError:
            Print('There was an error closing the file')

def get_menu():
    # Present the user with the main menu of options.
    # Return user input to main. 


    # Present menu of options
    print('Please select from the following options: ')
    print()
    print("""1. Add new note  
2. Search 
3. Combobulate
4. Quit""")
    print()

    # Take user selection
    user_choice = input()

    # Validate user selection 
    while user_choice not in ['1', '2', '3', '4']:
        print('Invalid option')
        user_choice = input('>>> ')


    # Return user selection
    return user_choice

def new_note():
    # Allows user to enter notes in the form of a string
    # along with any number of keys they would like
    # the note to be copied under. 

    
    # Get_notes is now called in every function
    # that modifies notes rather than having
    # notes assigned from main in an effort
    # to stem pervasive TypError issues
    notes = get_notes()
    
    print("""To add a new note:

1) Enter the full text of the note when prompted.
Note: pressing the enter key will submit the note.

2) Then, enter the keys the note should be filed under
seperated by a / e.g. Enter keys: #Socrates#Plato#Aristotle.
Note: keys are not case-sensitive and are filed in all-uppercase.

""")

    # Set again value
    again = 'y'

    # Set while loop
    while again.lower() == 'y':

        val_note = 'n'

        # Until the user indicates that the note satisfactory to them,
        # perhaps free of typos, for example, they will be prompted
        # to reenter it
        while val_note.lower() == 'n':
            
            # Take note as input
            note = input('Enter new note: ')
            print()

            #Validate that this note is correct
            val_note = input(f'The following note will be filed:\n\n{note}\n\nIs that correct? (y/n): ')
            print()
            #Validate validate note input
            while val_note.lower() not in ['y','n']:
                val_note = input('(y/n)? ')
                print()

        val_keys = 'n'

        # Likewise, the user must certify that they
        # have entered the keys as they wish
        # before they are recorded in the dictionary
        while val_keys.lower() == 'n':
        
            # Take keywords as input
            key_chain = input('Enter keys separated by #:  ')
            print()

            # Make keys upper
            key_chain = key_chain.upper()
           
            # Split into individual keys:
            keys = key_chain.split('#')

            # Validate keys
            val_keys = input(f'The note will be filed under: {keys}. Is that correct? (y/n): ')
            print()
            # Validate validate keys input
            while val_keys.lower() not in ['y','n']:
                val_keys = input('(y/n)? ')
                print()

        # Add note to dictionary under each key
        for key in keys:
            # If the key is already in the notes dictionary
            if key in notes:
                # create a set containing the existing notes
                note_set = notes[key]
                # add the note to the set
                note_set.add(note)
                # send the updated set to replace the original in the dictionary
                notes[key] = note_set
 
            else:
                # Otherwise, 
                notes[key] = set([note])

        # Update the file
        write_to_file(notes)

        # return notes
        return notes

        # Ask user to add another note or return to menu
        again = input('Would you like to add another note? (y/n): ')
        print()
        while again.lower() not in ['y','n']:
            again = input('(y/n)? ')
            print()

        print()
        
def write_to_file(notes):
    # Takes modified notes from calling function
    # and updates the pickled file
    
    try:
        # Open the notes.dat file in write mode
        outfile = open('notes.dat','wb')
        # Warn error
    except IOError:
        print('There was an error opening the file')

    # Pickle the notes and put them in the file
    pickle.dump(notes, outfile)
    
    print('Your notes file has been updated')
    print()

    try:
        # close the file
        outfile.close()
        # Warn error
    except IOError:
        print('There was an error closing the file')
        
def note_search():
    # Not search offers a menu of options
    # including printing a list of all keys
    # searching by key, searching by text,
    # and printing a random key along with
    # its contents. The key search and text
    # search also offer options to delete
    # notes and keys.

    # Get user choice
    user_choice = get_search_menu()

    if user_choice == '1':
        key_list()
    # elif 2, call delete note
    elif user_choice == '2':
        key_search()
    # if 3, call note search
    elif user_choice == '3':
        text_search()
    #elif 4, call program_exit
    elif user_choice == '4':
        random_key()
        
def get_search_menu():
    # Present the user with a menu of options.
    # Return user input to note_search. 

    # Present menu of options
    print('Please select from the following options: ')
    print()
    print("""1. Print list of keys  
2. Search by key
3. Search by text
4. Random note """)
    print()

    # Take user selection
    user_choice = input()

    # Validate user selection 
    while user_choice not in ['1', '2', '3', '4']:
        print('Invalid option')
        user_choice = input('>>> ')


    # Return user selection
    return user_choice

def key_list():
    # Prints a list of keys currently in the notes dictionary
    
    notes = get_notes()

    #create empty keys list
    key_list = []
    
    # for each key in the notes dictionary
    for key in notes:
        # add to key list
        key_list.append(key)

    # Sort keylist
    key_list.sort()

    count = 0

    for key in key_list:
        count += 1
        print(f'{count}.{key}')

       
def key_search():
    # Takes a search term and either displays
    # notes that appear under the key so-named
    # or alerts that no such key was found.
    # Offers option to delete the key.
    
        notes = get_notes()
        
        # Set again
        again = 'y'

        # While again is yes
        while again == 'y':
                    
            try:
                # Take input for key_search
                key_search = input('Enter key to lookup: ')
                # And make it uppercase to mach the key formatting
                key_search = key_search.upper()

                # Alert that key was found
                if key_search in notes:
                    print()
                    print(f'NoteMaster 10000 found the following notes filed under {key_search}:')
                    print()
                

                # Set counter that will enumerate list of notes
                count = 0
                # For every note filed under the key
                for var in notes[key_search]:
                    # Increase the counter by one
                    count += 1
                    # And display the enumerated  note
                    print(f'{count}.\n{var}')
                    print()

            # Warn that key was not found
            except KeyError:
                print('That key was not found. Try again.')
                print()

            # Display delete menu and get user choice
            user_choice = delete_menu()

            # Search again
            if user_choice == '1':
                again = 'y'
            # Delete the current key
            elif user_choice == '2':
                delete_key(key_search)
                again = input('Would you like to make another search? (y/n) ')
                while again.lower() not in ['y','n']:
                    again = input('(y/n)? ')
                    print()
            # Or return to the main menu
            elif user_choice == '3':
                again = 'n'

def delete_key(key):
    # Takes the current key from key search
    # and deletes the key as well as all
    # instances of notes filed under the key

    notes = get_notes()

    # Validate that the user intends to delete the key, etc.
    delete = input(f"""Are you sure you want NoteMaster 10000 to permanently delete the key {key} 
along with any instances of notes filed under it? (y/n) """)
    # Validate input
    while delete.lower() not in ['y','n']:
        delete = input('(y/n)? ')
    print()

    # Delete the key and its contents
    if delete == 'y':
        notes.pop(key)
        print(f'Zap! NoteMaster 10000 has deleted the key {key} and all its associated notes')
        # Update file
        write_to_file(notes)
                               
def text_search():
    # Allows users to input text and then searches
    # the notes in the dictionary for matching strings.
    # When a note with a matching string is found
    # The entire note is displayed - all in lowercase,
    # except for the searched text, which is highlighted
    # in uppercase.
    
    # Set again
    again = 'y'

    # While again is yes
    while again == 'y':

            # Create a blank results list.
            # This list will be populated with the
            # notes found and modified (upper/lower)
            # by the block of code below
            results = []
            
            # Input the text to be searched for
            text_search = input('Enter text to lookup: ')
            # Make lowercase. The lowercase text will
            # be compared to lowercase versions of all
            # the notes in the notes dictionary
            text_search = text_search.lower()
            print()

            notes = get_notes()

            try:
                # For every key:note element in the notes dictionary
                for key in notes:
                    # Look at each note
                    for var in notes[key]:
                        # Make it lowercase
                        var = str(var).lower()
                        # And if the input text matches text in the note
                        if text_search in var:
                            # Make all the text of the note lowercase
                            # except that matching the input text
                            # which is made uppercase
                            var = str(var).replace(text_search, text_search.upper())
                            result = (var)
                            # If the modified note is not already in the results list
                            if var not in results:
                                # Add the modified note to the results list
                                results.append(result)
            # To deal with a TypeError that I can't make heads or tails of                   
            except TypeError:
                print('Huh! NoteMaster 10000 lost track of the notes ...')

            temp_dict = {}
           # Set count to 0           
            count = 0
            # For each modified note in the results list
            for result in results:
                # Increase counter by 1
                count += 1
                # Add to temp_dict
                temp_dict[count] = result
                # And print the enumerated note
                print(f'{count}.\n{temp_dict[count]}')
                print()

            # Notify user if no matches were found
            if results == []:
                print(f'Sorry, NoteMaster 10000 found no notes containing "{text_search}"')
                print()

            # Display menu and get user choice
            user_choice = delete_menu()

            # Search again
            if user_choice == '1':
                again = 'y'
            # Delete all copies of a note across all keys
            elif user_choice == '2':
                delete_text(temp_dict)
                again = input('Would you like to make another search? (y/n) ')
                while again.lower() not in ['y','n']:
                    again = input('(y/n)? ')
                    print()
            # Return to main menu
            elif user_choice == '3':
                again = 'n'

def delete_text(temp_dict):
    # Recieves a dictionary of notes supplied by text_search
    # and allows the user to delete every instance of one
    # of those notes from the entire notes dictionary

    # Get notes
    notes = get_notes()

    # Print the options for notes to be deleted
    for note in temp_dict:
        print(note, temp_dict[note])
        print()

    # Create empty list to be populated
    # with numbers from 1 up to the number
    # of notes in the temp_dict
    choice_range = []

    # populate the list
    for num in range(len(temp_dict)):
        choice_range.append(num+1)

    try:
        # Input user choice for note to delete
        delete_choice = int(input('Enter the number of the note to delete: '))
        # Attempt to validate input
        if delete_choice not in choice_range:
            delete_choice = input('Enter the number of the note to delete: ')
        # Warn error - input was not an integer
    except ValueError:
        print('Number of note must be entered as an integer')
        print()

    # Create an empty list to hold all the keys the note falls under
    key_list = []
    # Create an empty string variable to hold the note
    del_var = ''
    
    # To avoid a runtime error trying to change set while iterating over it
    try:
        # For each key in the notes dictionary
        for key in notes:
            # Look at each note
            for var in notes[key]:
                # If the note chose by the user for deletion matches the note
                if temp_dict[delete_choice].lower() == var.lower():
                    # Send the key to the key list
                    key_list.append(key)
                    # And assign the properly formatted note, as it appears in the dictionary, to the empty string
                    del_var = var
    # Warn - this is the second error that stems from
    # delete choice not being an integer
    except UnboundLocalError:
        print('NoteMaster 10000 is confused.')

    print()
    # Ensure the user wants to delete all occurrences of the note
    double_check = input(f"""Are you sure you want NoteMaster 10000 to permanently delete
all occurences of the note from the notes file? (y/n) """)
    # Validate input
    while double_check.lower() not in ['y','n']:
        double_check = input('(y/n)? ')
    print()
    
    while double_check == 'y':
        # For each key in the key list
        for key in key_list:
            # Find and discard the note under that key
            notes[key].discard(del_var)
            print('Zap! The note has been deleted.')
            # Save changes to file
            write_to_file(notes)
            # If these deletions leave an empty key
            if len(notes[key]) == 0:
                print(f'NoteMaster 10000 found the key {key} is empty so has deleted it.')
                # Delete the key
                notes.pop(key)
                # And save that change too
                write_to_file(notes)
            double_check = 'n'

    
            
def delete_menu():
    # Present the user with a menu of options.
    # Return user input to note_search. 

    # Present menu of options
    print('Please select: ')
    print()
    print("""1. Make another search. 
2. Delete one of the items above.
3. Return to menu. 
""")
    print()

    # Take user selection
    user_choice = input()

    # Validate user selection 
    while user_choice not in ['1', '2', '3',]:
        print('Invalid option')
        user_choice = input('>>> ')


    # Return user selection
    return user_choice
    
def random_key():
    # Picks a key at random and displays
    # all the notes filed under it
    
    notes = get_notes()

    input("""Random Note will randomly select a key from your notes file
and display all the notes filed under that key.""")
    print()

    again = 'y'

    # Set while loop
    while again == 'y':

        # Set count 0
        count = 0
        # Obtain random number within range of the length of the dictionary
        num = random.randrange(len(notes))

        # For each key in the dictionary
        for key in notes:
            # Increase the count by 1
            count += 1
            # Until it matches the random number
            if count == num:
                # Then print the key
                print(key)
                # Set count 0
                counter = 0
                # For each note under the key
                for var in notes[key]:
                    # Increase the count by 1
                    counter += 1
                    # And print the enumerated note
                    print(f'{counter}.\n{var}')
                    print()
                    
        # Ask user if they would like to search again
        again = input('Would you like NoteMaster 10000 to draw another random key? (y/n): ')
        # Validate input
        while again.lower() not in ['y','n']:
            again = input('(y/n)? ')

def combobulator():
    # The combobulator randomly selects notes
    # from the notes dictionary, scrubs them,
    # and compares them to eachother
    # until it finds two that share
    # at least one word in common.
    # It then displays the two notes
    # and a list of the common words
    # between them, presented as
    # "points of possible conceptual convergence".

    notes = get_notes()
    
    input("""Welcome to The Combobulator!
The Combobulator randomly selects two notes
and displays them along with a curated list of words
common to both notes. The purpose of the Combobulator
is to spark new and unexpected insights into
the connections between ideas.

PLEASE BE PATIENT WHILE THE COMBOBULATOR THINKS.

Press Enter to Combobulate!

""")

    # Create empty results set
    results = set()
  
    # While results set is empty the program will
    # continue to search for two notes with at least
    # one valid word in common
    while results == set():

        # Create empty combulator set.  
        comb_set = set()
        
        # While there are fewer than two notes in the combobulator set
        # the program will continue to search for notes that are not
        # already in the combobulator set
        while len(comb_set) < COMBOBULATOR_NUM:
            
            try:
                # Obtain random number within the range of the number of elements in the notes dictionary
                num1 = random.randrange(len(notes))
            # Warn error
            except ValueError:
                print()
                print('Oops. There appear to be no notes to combobulate.')
        
            # Set count1
            count1 = -1

            # Use the random number to select a key:note(s) pair
            # from the notes dictionary.
            
            # For each element in the dictionary:
            for key in notes:
                # Add 1 to count 1
                count1 += 1
                # Until counter is equal to the random number
                # now key:note(s) pair that will be processed
                # has been selected
                if count1 == num1:
                    
                    # Then pick a random note associated with the key by:
                    
                    # Obtain random number within the range of notes associated with the key               
                    num2 = random.randrange(len(notes[key]))

                    # Set count2
                    count2 = -1

                    # For each note associated with the key 
                    for var in notes[key]:
                        # Add 1 to count2
                        count2 += 1
                        # Until counter is equal to the random number
                        # now the note that will be sent to the
                        # combobulator set has been selected
                        if count2 == num2:
                            # Then add the note to the combobulator set
                            comb_set.add(var)
                            

        # This block cleans the notes in the combobulator set of punctuation and common words
        # then creates a set of the remaining words in the note

        # Create an empty temporary dictionary.
        # This dictionary will be populated with
        # two elements, 1:{a set containing the
        # scrubbed words from note 1} and
        # 2:{a set containing those from note 2}
        temp_dict = {}

        # Set count to 0. Since the following block will
        # run twice, once for each item in the combobulator set
        # the count will furnish the 1 and 2 for the temp_dict keys
        count = 0

        # For each of the 2 notes in the combobulator set
        for var in comb_set:
            # Increase the count by 1
            count += 1
            # Create an empty temporary set. This set will be
            # populated with the scrubbed words of the note
            # and sent to the temp_dict as a variable
            temp_set = set()
            # split the note into a list of words
            var_list = var.split()

            # for each word in the list
            for element in var_list:
                # scrub punctation
                for punct in PUNCT:
                    element = element.strip(punct)
                    element = element.lstrip(punct)
                # scrub common words
                if element.lower() not in COMMS and element != '':
                    # and send to temp set
                    
                    temp_set.add(element.lower()) # now you have a scrubbed set of words from the note
                    temp_dict[count] = temp_set
                    # Let user know the program is going through its motions
                    print('...')
                   
        # results are the intersection of the two sets of words
        results = temp_dict[1] & temp_dict[2]

    # Print the notes and the keys they're filed under
    for var in comb_set:
        print()
        print(f'NOTE:', var)
        print()
        print('Filed under:')
        for key in notes:
            if var in notes[key]:
                print(key)
        
    print()
    # Show the word(s) from the interesection of both sets of words
    print(f"""The combobulator has analyzed the two notes and has identified
the following words as points of potential conceptual convergence:""")
    count = 0
    for item in results:
        count +=1
        print(count, item)
    print()


# Run program      
if __name__ == '__main__':
    main()

