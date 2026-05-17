// =========================
// 地図生成
// =========================

// mapオブジェクト生成
const map = L.map('map');

// 仮の初期位置（大阪駅）
map.setView([34.7025, 135.4959], 13);

// =========================
// 地図タイル追加
// =========================

L.tileLayer(
    'https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png',
    {
        attribution:
        '&copy; OpenStreetMap contributors'
    }
).addTo(map);


// =========================
// DB投稿表示
// =========================

posts.forEach(post => {

    // 緯度経度存在時のみ
    if(post.latitude &&
       post.longitude){

        L.marker([
            post.latitude,
            post.longitude
        ])
        .addTo(map)
        .bindPopup(`
        <div class="popup">

            <h3>${post.content}</h3>

            ${post.imagePath
                ? `<img src="/uploads/${post.imagePath}" width="200">`
                : ""
            }

            <p>緯度: ${post.latitude.toFixed(5)}</p>
            <p>経度: ${post.longitude.toFixed(5)}</p>

        </div>
        `);
    }

});


// =========================
// 現在地取得
// =========================

if (navigator.geolocation) {

    navigator.geolocation.getCurrentPosition(

        function(position){

            const lat =
                position.coords.latitude;

            const lng =
                position.coords.longitude;

            // 現在地へ移動
            map.setView(
                [lat, lng],
                13
            );

            // 現在地ピン表示
            L.marker(
                [lat, lng]
            )
            .addTo(map)
            .bindPopup(
                "現在地"
            )
            .openPopup();

        },

        function(){

            console.log(
                "現在地取得失敗"
            );

        }

    );

}


// =========================
// 地図クリック
// =========================

let currentMarker = null;

map.on(

    'click',

    function(e){

        const lat =
            e.latlng.lat;

        const lng =
            e.latlng.lng;

        // 緯度入力
        document.getElementById(
            "lat"
        ).value =
            lat.toFixed(6);

        // 経度入力
        document.getElementById(
            "lng"
        ).value =
            lng.toFixed(6);

        // 古いピン削除
        if(currentMarker){

            map.removeLayer(
                currentMarker
            );

        }

        // 新ピン表示
        currentMarker =
            L.marker(
                [lat, lng]
            )
            .addTo(map);

    }

);
