function send(name, seed, score) {
    $.ajax({
        url: "https://docs.google.com/forms/d/e/1FAIpQLScc0046kXez3XgQFkYFE-wCYqIqtbdRm5IYxJ2Hd4qzRH3NYA/formResponse",
        data: {
            "entry.1757642521": name,
            "entry.614906427": seed,
            "entry.1280865127": score
        },
        type: "POST",
        dataType: "xml"
    })
}