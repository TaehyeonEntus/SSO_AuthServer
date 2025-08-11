export async function loadProtectedData(endpoint) {
    try {
        const res = await fetch(endpoint, {
            method: "GET",
            credentials: "include"
        });

        if (res.status === 401) {
            const errorData = await res.json();

            if (errorData.error === "access_token_error") {
                const refreshRes = await fetch("/token/refresh", {
                    method: "POST",
                    credentials: "include"
                });

                if (refreshRes.ok) {
                    const retryRes = await fetch(endpoint, {
                        method: "GET",
                        credentials: "include"
                    });

                    if (retryRes.ok) {
                        return await retryRes.json();
                    } else {
                        window.location.href = "/login";
                        return null;
                    }
                } else {
                    window.location.href = "/login";
                    return null;
                }
            } else if (errorData.error === "refresh_token_error") {
                window.location.href = "/login";
                return null;
            } else {
                console.error("Unauthorized access");
                window.location.href = "/login";
                return null;
            }
        } else if (res.ok) {
            return await res.json();
        } else {
            return null;
        }
    } catch (error) {
        window.location.href = "/login";
        return null;
    }
}
