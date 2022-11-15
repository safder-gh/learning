package com.example.hammadhanif.availabilityapplication.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hammadhanif.availabilityapplication.R;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class FileChooser {
    private static final String PARENT_DIR = "..";

    private final Activity activity;
    private ListView list;
    private Dialog dialog;
    private File currentPath;
    private ImageButton imgBtnRevert;
    private View empty_view;

    // filter on file extension
    private String extension = null;
    public void setExtension(String extension) {
        this.extension = (extension == null) ? null :
                extension.toLowerCase();
    }

    // file selection event handling
    public interface FileSelectedListener {
        void fileSelected(File file);
    }
    public FileChooser setFileListener(FileSelectedListener fileListener) {
        this.fileListener = fileListener;
        return this;
    }
    private FileSelectedListener fileListener;

    public FileChooser(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.file_chooser);
        list = dialog.findViewById(R.id.lstFiles);//new ListView(activity);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
                String fileChosen = ((MyFile) list.getItemAtPosition(which)).name;
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    refresh(chosenFile);
                } else {
                    if (fileListener != null) {
                        fileListener.fileSelected(chosenFile);
                    }
                    dialog.dismiss();
                }
            }
        });
        Button btnCancel = dialog.findViewById(R.id.btn_no);
        btnCancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        imgBtnRevert = dialog.findViewById(R.id.imgBtnRevert);
        imgBtnRevert.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                refresh(((File)v.getTag()).getParentFile());
            }
        });
        empty_view = dialog.findViewById(R.id.empty_view);
        //dialog.setContentView(list);
        //dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        refresh(Common.filePath(activity));
    }

    public void showDialog() {
        dialog.show();
    }


    /**
     * Sort, filter and display the files for the given path.
     */
    private void refresh(File path) {
        this.currentPath = path;
        imgBtnRevert.setTag(path);
        if (path.exists()) {
            File[] dirs = path.listFiles(new FileFilter() {
                @Override public boolean accept(File file) {
                    return (file.isDirectory() && file.canRead());
                }
            });
            File[] files = path.listFiles(new FileFilter() {
                @Override public boolean accept(File file) {
                    if (!file.isDirectory()) {
                        if (!file.canRead()) {
                            return false;
                        } else if (extension == null) {
                            return true;
                        } else {
                            return file.getName().toLowerCase().endsWith(extension);
                        }
                    } else {
                        return false;
                    }
                }
            });
            if(dirs == null && files == null) {
                imgBtnRevert.setEnabled(false);
                return;
            }else {
                imgBtnRevert.setEnabled(true);
            }
            // convert to an array
            int i = 0;
            MyFile[] fileList;
            if (path.getParentFile() == null) {
                fileList = new MyFile[dirs.length + files.length];
            } else {
                fileList = new MyFile[dirs.length + files.length ];
                //fileList[i++] = PARENT_DIR;
            }
            Arrays.sort(dirs);
            Arrays.sort(files);
            for (File dir : dirs) { fileList[i++] = new MyFile(dir.getName(),FileType.folder); }
            for (File file : files ) { fileList[i++] = new MyFile(file.getName(),FileType.file); }
            if(fileList.length == 0){
               list.setVisibility(View.INVISIBLE);
               this.empty_view.setVisibility(View.VISIBLE);
            }
            else{
                list.setVisibility(View.VISIBLE);
                this.empty_view.setVisibility(View.GONE);
            }

            // refresh the user interface
            //dialog.setTitle(currentPath.getPath());
            list.setAdapter(new FileAdapter(activity,R.layout.fl_list_item,fileList));
            /*list.setAdapter(new ArrayAdapter(activity,R.layout.fl_list_item, fileList) {
                @Override public View getView(int position, View view, ViewGroup parent) {
                    String fileName = (String)getItem(position);
                    view = super.getView(position, view, parent);
                    ((TextView) view).setSingleLine(true);
                    return view;
                }
            });*/
        }
    }
private enum FileType{
    file,
    folder
}
private class MyFile {
    private String name;
    private FileType fileType;
    public MyFile(String name, FileType fileType) {
        this.name = name;
        this.fileType = fileType;
    }
}
private class FileAdapter extends  ArrayAdapter<MyFile> {

    public FileAdapter(Context context, int resource, MyFile[] objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MyFile myFile = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fl_list_item, parent, false);
        }
        // Lookup view for data population
        TextView fname = (TextView) convertView.findViewById(R.id.fname);

        // Populate the data into the template view using the data object
        fname.setText(myFile.name);
        if(myFile.fileType == FileType.file)
            fname.setCompoundDrawablesWithIntrinsicBounds(getContext().getDrawable(R.drawable.ic_files),null,null,null);
        else
            fname.setCompoundDrawablesWithIntrinsicBounds(getContext().getDrawable(R.drawable.ic_folder),null,null,null);
        // Return the completed view to render on screen
        return convertView;
    }

}
    /**
     * Convert a relative filename into an actual File object.
     */
    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) {
            return currentPath.getParentFile();
        } else {
            return new File(currentPath, fileChosen);
        }
    }
}