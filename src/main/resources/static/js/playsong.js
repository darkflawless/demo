const mainContent = document.getElementById('mainContent');

// Hàm phát nhạc khi người dùng nhấn Play
async function playSong(songUrl, songName) {
    try {
        // Cập nhật nội dung bên phải với giao diện player
        mainContent.innerHTML = `
            <div class="player-container">
                <h2>Now Playing: ${songName}</h2>
                <audio id="audioPlayer" controls autoplay style="width: 100%;">
                    <source src="${songUrl}" type="audio/mp3">
                    Your browser does not support the audio element.
                </audio>
                <button onclick="goBack()">Back to Song List</button>
            </div>
        `;

        // Tìm player mới được tạo và phát nhạc
        const audioPlayer = document.getElementById('audioPlayer');
        await audioPlayer.play();  // Phát nhạc tự động
    } catch (error) {
        console.error('Error playing song:', error);
    }
}

// Hàm quay lại danh sách bài hát
function goBack() {
    location.reload();  // Tải lại trang
}
