document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll('.play-btn');
    const audioPlayer = document.getElementById('audio-player');
    const audioSource = audioPlayer.querySelector('source');
    const coverImage = document.getElementById('cover-image');
    const canvas = document.getElementById('audioVisualizer');

    // Add click event to play buttons
    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const songUrl = button.getAttribute('data-link');
            const coverUrl = button.getAttribute('data-cover');
            playSong(songUrl, coverUrl);
        });
    });

    async function playSong(songId, songUrl, coverUrl) {
        try {
            // Gọi API để tăng view cho bài hát
            await fetch(`/songs/${songId}/incrementView`, { method: 'PUT' });

            // Cập nhật cover và nguồn audio
            coverImage.src = coverUrl;
            audioSource.src = songUrl;

            // Đảm bảo audio được tải và phát
            await audioPlayer.load();
            audioPlayer.play();
            setupAudioVisualizer(audioPlayer, canvas);

            // Fetch random songs sau khi phát bài hát
            audioPlayer.addEventListener('playing', fetchRandomSongs);

        } catch (error) {
            console.error('Error playing the song:', error);
        }
    }


    audioPlayer.onended = function() {
        fetch('/randomSong')
            .then(response => response.json())
            .then(song => {
                audioSource.src = song.link; // Update the audio source
                coverImage.src = song.cover; // Update the cover image

                audioPlayer.load(); // Ensure the audio is loaded
                audioPlayer.play();
            })
            .catch(error => console.error('Error fetching random song:', error));
    };


    window.playSong = playSong; // Make playSong globally accessible

    function goBack() {
        location.reload();
    }
});