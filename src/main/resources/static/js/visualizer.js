function setupAudioVisualizer(audioElement, canvasElement) {
    const canvasContext = canvasElement.getContext('2d');

    const audioContext = new (window.AudioContext || window.webkitAudioContext)();
    const audioSource = audioContext.createMediaElementSource(audioElement);
    const analyzer = audioContext.createAnalyser();

    audioSource.connect(analyzer);
    analyzer.connect(audioContext.destination);
    analyzer.fftSize = 128;
    analyzer.smoothingTimeConstant = 0.85; // Thêm một chút trơn cho dữ liệu

    const bufferLength = analyzer.frequencyBinCount;
    const dataArray = new Uint8Array(bufferLength);

    function draw() {
        requestAnimationFrame(draw);
        analyzer.getByteFrequencyData(dataArray);

        canvasContext.clearRect(0, 0, canvasElement.width, canvasElement.height);
        const barWidth = (canvasElement.width / bufferLength) * 2.5;
        let barHeight;
        let x = 0;

        for (let i = 0; i < bufferLength; i++) {
            barHeight = dataArray[i] / 2;

            // Biến đổi giá trị biên độ thành giá trị màu
            const hue = (barHeight / canvasElement.height) * 360; // Thay đổi phạm vi màu sắc theo biên độ
            let color;

            if (hue < 10) {
                // Tăng cường màu đỏ và cam
                color = `hsl(${hue}, 100%, 50%)`; // Màu đỏ và cam ở phạm vi 0-30 độ trong mô hình HSL
            } else if (hue < 40) {
                color = `hsl(${10 + (hue - 10) / 2}, 100%, 50%)`; // Tăng cường màu cam bằng cách nén phạm vi 30-60 độ
            } else {
                color = `hsl(${40 + (hue - 40) * 0.85}, 100%, 50%)`; // Nén các màu còn lại để có không gian nhiều hơn cho màu đỏ và cam
            }

            canvasContext.fillStyle = color;
            canvasContext.fillRect(x, canvasElement.height - barHeight, barWidth, barHeight);

            x += barWidth + 1; // Spacing between bars
        }


    }

    audioElement.addEventListener('play', () => {
        audioContext.resume(); // Ensure audio context is activated
        draw();
    });
}