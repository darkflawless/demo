document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById('searchInput');
    const songItemsContainer = document.querySelector('.song-list');

    // Add input event to search box
    searchInput.addEventListener('input', async function () {
        const query = searchInput.value.toLowerCase();
        if (query) {
            try {
                const response = await fetch(`/searchSongs?query=${query}`);
                const songs = await response.json();
                updateSongList(songs);
            } catch (error) {
                console.error('Error searching for songs:', error);
            }
        } else {
            // Fetch top 3 songs when search input is cleared
            fetchTopSongs();
        }
    });

    async function fetchTopSongs() {
        try {
            const response = await fetch('/topSongs');
            const songs = await response.json();
            updateSongList(songs);
        } catch (error) {
            console.error('Error fetching top songs:', error);
        }
    }

    function updateSongList(songs) {
        songItemsContainer.innerHTML = ''; // Clear existing content

        songs.forEach(song => {
            const songItem = document.createElement('div');
            songItem.className = 'song-item';
            songItem.style = 'background-size: cover; padding: 20px; margin-bottom: 10px; color: white;';

            const songDiv = document.createElement('div');
            songDiv.className = 'song-cover play-btn';
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
            songName.style.width = '120px'; // Ensure it matches the size of songDiv for consistency
            songItem.appendChild(songName);

            songItemsContainer.appendChild(songItem);
        });
    }

    // Fetch top 3 songs on initial load
    fetchTopSongs();
});