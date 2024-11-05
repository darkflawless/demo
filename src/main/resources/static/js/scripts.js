document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll('.play-btn');
    const audioPlayer = document.getElementById('audio-player');
    const audioSource = audioPlayer.querySelector('source');
    const coverImage = document.getElementById('cover-image');
    const canvas = document.getElementById('audioVisualizer');
;

    fetchRandomSongs(); // Fetch initial random songs list

    // Add click event to play buttons
    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const songUrl = button.getAttribute('data-link');
            const coverUrl = button.getAttribute('data-cover');
            playSong(songUrl, coverUrl);
        });
    });



    async function playSong(songUrl, coverUrl) {
        try {
            coverImage.src = coverUrl;
            audioSource.src = songUrl;
            await audioPlayer.load(); // Đảm bảo rằng audio đã được load
            audioPlayer.play();
            setupAudioVisualizer(audioPlayer, canvas);

            // Fetch random songs after playing the song
            audioPlayer.addEventListener('playing', fetchRandomSongs);

        } catch (error) {
            console.error('Error playing the song:', error);
        }
    }

    window.playSong = playSong; // Make playSong globally accessible

    function goBack() {
        location.reload();
    }

    async function fetchRandomSongs() {
        try {
            const response = await fetch('/randomSongs');
            const songs = await response.json();
            updateRandomSongs(songs);
        } catch (error) {
            console.error('Error fetching random songs:', error);
        }
    }

    async function toggleFavorite(songId) {
        try {
            const response = await fetch(`/toggleFavorite/${songId}`, { method: 'POST' });
            if (response.ok) {
                fetchRandomSongs(); // Làm mới danh sách bài hát
            }
        } catch (error) {
            console.error('Error toggling favorite status:', error);
        }
    }



    function updateRandomSongs(songs) {
        const randomSongsContainer = document.querySelector('.recommended-songs ul');
        randomSongsContainer.innerHTML = ''; // Clear existing content

        songs.forEach(song => {
            const songItem = document.createElement('li');
            songItem.className = 'song-item';
            songItem.style = 'background-size: cover; padding: 20px; margin-bottom: 10px; color: white;';

            const songDiv = document.createElement('div');
            songDiv.style = `background-image: url(${song.cover}); background-size: cover; height: 120px; width: 120px; margin-bottom: 10px; cursor: pointer;`;
            songDiv.setAttribute('data-link', song.link);
            songDiv.setAttribute('data-cover', song.cover);
            songDiv.addEventListener('click', function () {
                playSong(song.link, song.cover);
            });

            songItem.appendChild(songDiv);

            const songName = document.createElement('span');
            songName.textContent = song.name;
            songName.style.display = 'block';
            songName.style.whiteSpace = 'nowrap';
            songName.style.overflow = 'hidden';
            songName.style.textOverflow = 'ellipsis';
            songName.style.width = '120px'; // Đảm bảo giống với kích thước của songDiv để giữ đồng nhất
            songItem.appendChild(songName);

            randomSongsContainer.appendChild(songItem);
        });
    }
});