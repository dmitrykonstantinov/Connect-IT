package breakingumbrella.connectit.entity.entities;

import java.io.Serializable;

public enum GameStateLegacy implements Serializable {
	WaitingForPlayers,
	ReadyToPlay,
	InProgress,
	ScheduledToDelete
}
