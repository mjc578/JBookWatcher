package com.example.owner.jbookwatcher.fragments;


import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.jbookwatcher.Book;
import com.example.owner.jbookwatcher.DateLibrary;
import com.example.owner.jbookwatcher.adapters.BookListAdapter;
import com.example.owner.jbookwatcher.data.BookDbHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentlyReadingFrag extends Fragment {

    private final int LIST_IDENT = 0;
    private ArrayList<Book> bookList;
    private BookListAdapter bookAdapter;
    private ListView list;
    private TextView noBooks;
    private DateLibrary ul;
    private BookDbHelper dbHelper;

    public CurrentlyReadingFrag() {
        // Required empty public constructor
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View crView = inflater.inflate(R.layout.fragment_currently_reading, container, false);
        ul = new DateLibrary();

        //instance our database
        dbHelper = new BookDbHelper(getContext());
        //load list of books that belong to this list category
        bookList = dbHelper.getListEntries(LIST_IDENT);
        bookAdapter = new BookListAdapter(getContext(), bookList);

        list = crView.findViewById(R.id.curr_read_list_view);
        noBooks = crView.findViewById(R.id.curr_list_text_view);

        //set list adapter on book list if there are any in db
        if(!bookList.isEmpty()){
            noBooks.setVisibility(View.GONE);
            list.setAdapter(bookAdapter);
            list.setVisibility(View.VISIBLE);
        }

        setFabulousButton(crView);
        setOnListListener();

        return crView;
    }

    public AlertDialog makeAlertDLog(View v, String title){
        return new AlertDialog.Builder(getContext())
                .setView(v)
                .setTitle(title)
                .setPositiveButton("Done", null)
                .setNegativeButton("Cancel", null)
                .create();
    }

    public Book makeBook(String title, View dcrView){
        Book book = new Book(title, LIST_IDENT);
        //check for other fields
        EditText etAuthor = dcrView.findViewById(R.id.edit_text_author_curr);
        if (!etAuthor.getText().toString().equals("")) {
            book.setAuthor(etAuthor.getText().toString());
        }
        EditText etPageNum = dcrView.findViewById(R.id.edit_text_book_pages_curr);
        if (!etPageNum.getText().toString().equals("")) {
            book.setPageNum(Integer.parseInt(etPageNum.getText().toString()));
        }
        EditText etStartDate = dcrView.findViewById(R.id.edit_text_curr_read_start_date);
        if (!etStartDate.getText().toString().equals("")) {
            book.setStartDate(etStartDate.getText().toString());
        }
        return book;
    }

    private void setFabulousButton(final View crView) {
        crView.findViewById(R.id.fab_curr_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dcrView = View.inflate(getContext(), R.layout.currently_reading_dialog, null);
                //TODO: FIX THE BROKEN DOUBLE CLICK ON DATE EDIT TEXT
                dcrView.findViewById(R.id.edit_text_curr_read_start_date).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(dcrView);
                    }
                });
                final AlertDialog dLog = makeAlertDLog(dcrView, "Enter Book Info");
                dLog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        final Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                EditText etBookTitle = dcrView.findViewById(R.id.edit_text_book_title_curr);
                                EditText etAuthor = dcrView.findViewById(R.id.edit_text_author_curr);
                                if (!ul.editHasText(etBookTitle) || !ul.editHasText(etAuthor)) {
                                    Toast.makeText(getContext(), R.string.no_titleauth, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Book book = makeBook(etBookTitle.getText().toString(), dcrView);
                                //only proceed if can insert book into db
                                if(dbHelper.addBook(book)){
                                    bookList.add(book);
                                    dbHelper.addBook(book);
                                    dbHelper.printDbToLog();
                                    noBooks.setVisibility(View.GONE);
                                    list.setAdapter(bookAdapter);
                                }
                                else{
                                    Toast.makeText(getContext(), getString(R.string.book_in_db), Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                //Dismiss once everything is OK.
                                dLog.dismiss();
                            }
                        });
                    }
                });
                dLog.show();
            }
        });
    }

    private void datePicker(final View dcrView) {
        final Calendar myCalendar = Calendar.getInstance();

        final EditText dateET = dcrView.findViewById(R.id.edit_text_curr_read_start_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateET.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setOnListListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < list.getCount(); i++){
                    View v = list.getChildAt(i);
                    v.findViewById(R.id.selected_book_buttons).setVisibility(View.GONE);
                }
                if(view.findViewById(R.id.selected_book_buttons).getVisibility() == View.GONE){
                    view.findViewById(R.id.selected_book_buttons).setVisibility(View.VISIBLE);

                    setBookDeleteListener(view, position);
                    setBookEditListener(view, position);
                    setMoveListener(view, position);
                }
                else{
                    view.findViewById(R.id.selected_book_buttons).setVisibility(View.GONE);
                }
            }
        });
    }

    private void setBookDeleteListener(final View v, final int position){
        TextView deleteText = v.findViewById(R.id.selected_book_delete);
        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View vv) {
                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Book b = bookList.remove(position);
                                dbHelper.deleteBook(b);
                                dbHelper.printDbToLog();
                                list.setAdapter(bookAdapter);
                                if(bookList.isEmpty()){
                                    noBooks.setVisibility(View.VISIBLE);
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Delete this book?").setPositiveButton("Yes", dialogListener)
                        .setNegativeButton("No", dialogListener).show();
            }
        });

    }

    private void setBookEditListener(final View v, final int position){
        final TextView editText = v.findViewById(R.id.selected_book_edit);
        final Book currBook = bookList.get(position);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                final View dcrView = View.inflate(getContext(), R.layout.currently_reading_dialog, null);
                final EditText etStartDate = dcrView.findViewById(R.id.edit_text_curr_read_start_date);
                final EditText etAuthor = dcrView.findViewById(R.id.edit_text_author_curr);
                final EditText etPageNum = dcrView.findViewById(R.id.edit_text_book_pages_curr);
                final EditText etBookTitle = dcrView.findViewById(R.id.edit_text_book_title_curr);
                //fill edit texts with current data
                if(currBook.getStartDate() != null){
                    etStartDate.setText(currBook.getStartDate());
                }
                if(currBook.getAuthor() != null){
                    etAuthor.setText(currBook.getAuthor());
                }
                if(currBook.getPageNum() != -1){
                    etPageNum.setText(currBook.getPageNum() + "");
                }
                etBookTitle.setText(currBook.getBookTitle());
                //TODO: FIX THE BROKEN DOUBLE CLICK ON DATE EDIT TEXT
                dcrView.findViewById(R.id.edit_text_curr_read_start_date).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datePicker(dcrView);
                    }
                });
                final AlertDialog dLog = makeAlertDLog(dcrView, "Edit Book Info");
                dLog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        final Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                if (!ul.editHasText(etBookTitle) || !ul.editHasText(etAuthor)) {
                                    Toast.makeText(getContext(), R.string.no_titleauth, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                int i = bookList.indexOf(currBook);
                                Book book = makeBook(etBookTitle.getText().toString(), dcrView);
                                if(dbHelper.updateBook(currBook, book)){
                                    bookList.set(i, book);
                                    list.setAdapter(bookAdapter);
                                }
                                else{
                                    Toast.makeText(getContext(), getString(R.string.book_in_db), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //Dismiss once everything is OK.
                                dLog.dismiss();
                            }
                        });
                    }
                });
                dLog.show();
            }
        });
    }

    private void setMoveListener(final View view, final int position){
        //view is the view of the list item
        TextView moveButton = view.findViewById(R.id.selected_book_move);
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.move_title)
                        .setSingleChoiceItems(new String[]{"Finished Reading", "To Read"}, 0, null)
                        .setPositiveButton(R.string.move, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                Book b = bookList.remove(position);
                                list.setAdapter(bookAdapter);
                                int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                                //0 for Finished Reading, 1 for To Read
                                if(selectedPosition == 0){
                                    //ul.moveToFinishedReading(dbHelper, b);
                                }
                                else{
                                    //ul.moveToToRead(dbHelper, b);
                                }

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }
*/
}