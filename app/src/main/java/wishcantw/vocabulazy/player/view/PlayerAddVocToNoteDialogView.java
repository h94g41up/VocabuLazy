package wishcantw.vocabulazy.player.view;

import android.content.Context;
import android.util.AttributeSet;

import wishcantw.vocabulazy.R;
import wishcantw.vocabulazy.widget.DialogViewNew;

import java.util.LinkedList;

/**
 * Created by SwallowChen on 9/6/16.
 */
public class PlayerAddVocToNoteDialogView extends DialogViewNew {

    private static final int VIEW_RADIO_GROUP_RES_ID = R.id.player_add_voc_to_note_dialog_radio_group;
    private static final int VIEW_YES_RES_ID = R.id.player_add_voc_to_note_dialog_yes;
    private static final int VIEW_NO_RES_ID = R.id.player_add_voc_to_note_dialog_no;

    private PlayerAddVocToNoteRadioGroup mPlayerAddVocToNoteRadioGroup;
    public PlayerAddVocToNoteDialogView(Context context) {
        super(context);
    }

    public PlayerAddVocToNoteDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setYesOrNoId(VIEW_YES_RES_ID, VIEW_NO_RES_ID);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPlayerAddVocToNoteRadioGroup = (PlayerAddVocToNoteRadioGroup) findViewById(VIEW_RADIO_GROUP_RES_ID);
    }

    public void refreshNoteRadioGroup(LinkedList<String> linkedList) {
        mPlayerAddVocToNoteRadioGroup.refreshView(linkedList);
    }

    public int getCurrentCheckedNoteIndex() {
        return mPlayerAddVocToNoteRadioGroup.getCheckedRadioButtonId();
    }
}
