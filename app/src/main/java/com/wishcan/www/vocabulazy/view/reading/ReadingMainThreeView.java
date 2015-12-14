package com.wishcan.www.vocabulazy.view.reading;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;

import com.wishcan.www.vocabulazy.MainActivity;
import com.wishcan.www.vocabulazy.storage.Database;
import com.wishcan.www.vocabulazy.view.infinitethreeview.InfiniteThreeView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by swallow on 2015/12/5.
 */
public class ReadingMainThreeView extends InfiniteThreeView {

    private static final String TAG = ReadingMainThreeView.class.getSimpleName();

    private Context mContext;

    private Database mDatabase;

    public ReadingMainThreeView(Context context) {
        this(context, null);
    }

    public ReadingMainThreeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mDatabase = ((MainActivity) context).getDatabase();

    }

    public void addNewReadingPage(int lessonId){
        AddNewReadingPageAsyncTask asyncTask = new AddNewReadingPageAsyncTask(InfiniteThreeView.CENTER_VIEW_INDEX);
        asyncTask.execute(lessonId);
    }

    public void cleanOldReadingPage(int position) {

        if (position == CENTER_VIEW_INDEX)
            return;
        refreshItem(position, null);
    }

    private class AddNewReadingPageAsyncTask extends AsyncTask<Integer, Void, String> {

        private int positionIndex;

        public AddNewReadingPageAsyncTask(int positionIndex) {
            this.positionIndex = positionIndex;
        }

        @Override
        protected String doInBackground(Integer... params) {

            /** Get Reading data from database Here */
            // TODO: The input is the chosen lessonID, please use the id to load the content
            Log.d(TAG, "doInBackground - " + "lessonID: " + params[0]);

            JSONObject readingObject = mDatabase.getReadingContent(params[0]);

            String outputString = "null content";
            try {
                outputString = readingObject.getString("en_content");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /** This is the example string to be shown*/
            /*
            String outputString = "I am happy to join with you today in what will go down in history as the greatest demonstration for freedom in the history of our nation.\n" +
                    "Five score years ago, a great American, in whose symbolic shadow we stand today, signed the Emancipation Proclamation. This momentous decree came as a great beacon light of hope to millions of Negro slaves who had been seared in the flames of withering injustice. It came as a joyous daybreak to end the long night of captivity. \n" +
                    "But one hundred years later, the Negro still is not free. One hundred years later, the life of the Negro is still sadly crippled by the manacles of segregation and the chains of discrimination. One hundred years later, the Negro lives on a lonely island of poverty in the midst of a vast ocean of material prosperity. One hundred years later, the Negro is still languished in the corners of American society and finds himself in exile in his own land. So we have come here today to dramatize an shameful condition. \n" +
                    "In a sense we've come to our nation's Capital to cash a check. When the architects of our republic wrote the magnificent words of the Constitution and the Declaration of Independence, they were signing a promissory note to which every American was to fall heir. \n" +
                    "This note was a promise that all men, yes, black men as well as white men, would be guaranteed the unalienable rights of life, liberty, and the pursuit of happiness. \n" +
                    "It is obvious today that America has defaulted on this promissory note insofar as her citizens of color are concerned. Instead of honoring this sacred obligation, America has given the Negro people a bad check; a check which has come back marked \"insufficient funds.\"\n" +
                    "But we refuse to believe that the bank of justice is bankrupt. We refuse to believe that there are insufficient funds in the great vaults of opportunity of this nation. So we have come to cash this check- a check that will give us upon demand the riches of freedom and the security of justice.\n" +
                    "We have also come to this hallowed spot to remind America of the fierce urgency of now. This is no time to engage in the luxury of cooling off or to take the tranquilizing drug of gradualism. \n" +
                    "Now is the time to make real the promises of democracy. Now is the time to rise from the dark and desolate valley of segregation to the sunlit path of racial justice. Now is the time to lift our nation from the quicksands of racial injustice to the solid rock of brotherhood. Now is the time to make justice a reality for all of God's children. \n" +
                    "It would be fatal for the nation to overlook the urgency of the moment. This sweltering summer of the Negro's legitimate discontent will not pass until there is an invigorating autumn of freedom and equality. Nineteen sixty-three is not an end, but a beginning. Those who hope that the Negro needed to blow off steam and will now be content will have a rude awakening if the nation returns to business as usual. There will be neither rest nor tranquility in America until the Negro is granted his citizenship rights. The whirlwinds of revolt will continue to shake the foundations of our nation until the bright day of justice emerges. \n" +
                    "But there is something that I must say to my people who stand on the warm threshold which leads into the palace of justice. In the process of gaining our rightful place we must not be guilty of wrongful deeds. Let us not seek to satisfy our thirst for freedom by drinking from the cup of bitterness and hatred. We must forever conduct our struggle on the high plane of dignity and discipline. We must not allow our creative protest to degenerate into physical violence. Again and again we must rise to the majestic heights of meeting physical force with soul force. \n" +
                    "The marvelous new militancy which has engulfed the Negro community must not lead us to a distrust of all white people, for many of our white brothers, as evidenced by their presence here today, have come to realize that their destiny is tied up with our destiny. And they have come to realize that their freedom is inextricably bound to our freedom. We cannot walk alone. \n" +
                    "And as we walk, we must make the pledge that we shall march ahead. We cannot turn back. There are those who are asking the devotees of civil rights, \"When will you be satisfied?\" \n" +
                    "We can never be satisfied as long as the Negro is the victim of the unspeakable horrors of police brutality.\n" +
                    "We can never be satisfied as long as our bodies, heavy with the fatigue of travel, cannot gain lodging in the motels of the highways and the hotels of the cities.\n" +
                    "We cannot be satisfied as long as the Negro's basic mobility is from a smaller ghetto to a larger one. \n" +
                    "We can never be satisfied as long as our chlidren are stripped of their selfhood and robbed of their dignity by signs stating \"for whites only.\" \n" +
                    "We cannot be satisfied as long as a Negro in Mississippi cannot vote and a Negro in New York believes he has nothing for which to vote. \n" +
                    "No, no, we are not satisfied, and we will not be satisfied until justice rolls down like waters and righteousness like a mighty stream. \n" +
                    "I am not unmindful that some of you have come here out of great trials and tribulations. Some of you have come fresh from narrow jail cells. Some of you have come from areas where your quest for freedom left you battered by the storms of persecution and staggered by the winds of police brutality. You have been the veterans of creative suffering. Continue to work with the faith that unearned suffering is redemptive. \n" +
                    "Go back to Mississippi, go back to Alabama, go back to South Carolina, go back to Georgia, go back to Louisiana, go back to the slums and ghettos of our northern cities, knowing that somehow this situation can and will be changed. Let us not wallow in the valley of despair. \n" +
                    "I say to you today, my friends, so even though we face the difficulties of today and tomorrow, I still have a dream. It is a dream deeply rooted in the American dream. \n" +
                    "I have a dream that one day this nation will rise up and live out the true meaning of its creed: \"We hold these truths to be self-evident; that all men are created equal.\" \n" +
                    "I have a dream that one day on the red hills of Georgia the sons of former slaves and the sons of former slave owners will be able to sit down together at the table of brotherhood. \n" +
                    " I have a dream that one day even the state of Mississippi, a state sweltering with the heat of injustice, sweltering with the heat of oppression, will be transformed into an oasis of freedom and justice.\n" +
                    " I have a dream that my four little children will one day live in a nation where they will not be judged by the color of their skin but by the content of their character.\n" +
                    " I have a dream today.\n" +
                    "I have a dream that one day down in Alabama, with its vicious racists, with its governor having his lips dripping with the words of interposition and nullification, that one day right down in Alabama little black boys and black girls will be able to join hands with little white boys and white girls as sisters and brothers. \n"+
                    "I have a dream today. " +
                    "I have a dream that one day every valley shall be exhalted, every hill and mountain shall be made low, the rough places will be made plain, and the crooked places will be made straight, and the glory of the Lord shall be revealed, and all flesh shall see it together.\n" ;
            */
            /** Finally return the Reading data as a very long String, or even a bufferStream */

            // TODO: please return the final content string here, the function onPostExecute(String) will handle
            return outputString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            /** Show the the final result and add into ReadingMainThreeView*/
            /** Using refreshItem(int positionIndex, View itemView), note that middle page's index is 1,
             *  which will be the only page to be seen */

            ReadingMainScrollView readingMainScrollView = new ReadingMainScrollView(getContext());
            readingMainScrollView.setReadingContentText(s);
            readingMainScrollView.setReadingContentTextSize(15);
            refreshItem(positionIndex, readingMainScrollView);
        }


    }

}
