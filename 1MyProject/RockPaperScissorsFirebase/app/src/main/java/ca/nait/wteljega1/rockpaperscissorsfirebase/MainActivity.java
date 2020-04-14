package ca.nait.wteljega1.rockpaperscissorsfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button rock_button, paper_button, scissors_button;
    TextView tv_score;
    ImageView image_player, image_npc;

    int playerScore, computerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rock_button = (Button) findViewById(R.id.rock_button);
        paper_button = (Button) findViewById(R.id.paper_button);
        scissors_button = (Button) findViewById(R.id.scissors_button);
        tv_score = (TextView) findViewById(R.id.tv_track_score);
        image_player = (ImageView) findViewById(R.id.player_image);
        image_npc = (ImageView) findViewById(R.id.npc_image);

        rock_button.setOnClickListener(this);
        paper_button.setOnClickListener(this);
        scissors_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.rock_button:
            {
                image_player.setImageResource(R.drawable.rock);
                computerChoice("rock");
                tv_score.setText("Player: " + Integer.toString(playerScore) + "    Computer: " + Integer.toString(computerScore));
                break;
            }
            case R.id.paper_button:
            {
                image_player.setImageResource(R.drawable.paper);
                computerChoice("paper");
                tv_score.setText("Player: " + Integer.toString(playerScore) + "    Computer: " + Integer.toString(computerScore));
                break;
            }
            case R.id.scissors_button:
            {
                image_player.setImageResource(R.drawable.scissors);
                computerChoice("scissors");
                tv_score.setText("Player: " + Integer.toString(playerScore) + "    Computer: " + Integer.toString(computerScore));
                break;
            }
        }
    }

    private void computerChoice(String player_selection)
    {
        String computer_choice = "";
        Random random = new Random();

        // choose between 1 2 or 3
        int computer_choice_number = random.nextInt(3) + 1;

        if (computer_choice_number == 1)
        {
            computer_choice = "rock";
            image_npc.setImageResource(R.drawable.rock);
        }
        if (computer_choice_number == 2)
        {
            computer_choice = "paper";
            image_npc.setImageResource(R.drawable.paper);
        }
        if (computer_choice_number == 3)
        {
            computer_choice = "scissors";
            image_npc.setImageResource(R.drawable.scissors);
        }

        // Determining who is the winner, loser or if its a draw
        if (computer_choice == player_selection)
        {
            Toast.makeText(this, "Draw, Nobody Wins!", Toast.LENGTH_SHORT).show();
        }
        else if (player_selection == "rock" && computer_choice == "scissors")
        {
            playerScore++;
            Toast.makeText(this, "Rock Obliterates Scissors! You Win!", Toast.LENGTH_SHORT).show();
        }
        else if (player_selection == "rock" && computer_choice == "paper")
        {
            computerScore++;
            Toast.makeText(this, "Paper Covers Rock! Computer Wins!", Toast.LENGTH_SHORT).show();
        }
        else if (player_selection == "paper" && computer_choice == "rock")
        {
            playerScore++;
            Toast.makeText(this, "Paper Covers Rock! You Win!", Toast.LENGTH_SHORT).show();
        }
        else if (player_selection == "paper" && computer_choice == "scissors")
        {
            computerScore++;
            Toast.makeText(this, "Paper Gets Cut By Scissors! Computer Wins", Toast.LENGTH_SHORT).show();
        }
        else if (player_selection == "scissors" && computer_choice == "rock")
        {
            computerScore++;
            Toast.makeText(this, "Scissors Gets Crushed By Rock! Computer Wins!", Toast.LENGTH_SHORT).show();
        }
        else if (player_selection == "scissors" && computer_choice == "paper")
        {
            playerScore++;
            Toast.makeText(this, "Scissors Cuts Paper! You Win!", Toast.LENGTH_SHORT).show();
        }
    }

    // Logging out of fire base (Log out of the current account that you are signed into)
    public void logout (View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
