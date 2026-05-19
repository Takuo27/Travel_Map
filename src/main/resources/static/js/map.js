const map = L.map('map');

map.setView([34.7025, 135.4959], 13);

L.tileLayer(
    'https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png',
    {
        attribution: '&copy; OpenStreetMap contributors'
    }
).addTo(map);


// =========================
// DBピン表示
// =========================
posts.forEach(post => {

    if (post.latitude && post.longitude) {

        L.marker([post.latitude, post.longitude])
            .addTo(map)
        .bindPopup(
            `
            <div class="popup">

                <h3>${post.content}</h3>

                ${
                    post.imagePath
                    ?
                    `<img
                        src="/uploads/${post.imagePath}"
                        width="200">`
                    :
                    `画像なし`
                }

                <p>
                    緯度:
                    ${post.latitude.toFixed(5)}
                </p>

                <p>
                    経度:
                    ${post.longitude.toFixed(5)}
                </p>

            </div>
            `
        );
    }
});


// =========================
// 現在地取得
// =========================
if (navigator.geolocation) {

    navigator.geolocation.getCurrentPosition(function(pos){

        const lat = pos.coords.latitude;
        const lng = pos.coords.longitude;

        map.setView([lat, lng], 13);

        L.marker([lat, lng])
            .addTo(map)
            .bindPopup("現在地")
            .openPopup();

    });

}


// =========================
// クリックで位置設定
// =========================
let currentMarker = null;

map.on('click', function(e){

    const lat = e.latlng.lat;
    const lng = e.latlng.lng;

    document.getElementById("latitude").value =
        lat.toFixed(6);

    document.getElementById("longitude").value =
        lng.toFixed(6);

    console.log(
        document.getElementById("latitude").value
    );

    console.log(
        document.getElementById("longitude").value
    );
    if (currentMarker) {
        map.removeLayer(currentMarker);
    }

    currentMarker = L.marker([lat, lng]).addTo(map);

});
