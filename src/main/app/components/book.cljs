(ns app.components.book)

(defn book-row
  [{:keys [id author title publication-date]}]
  [:span   author ", " [:i  title] " (" publication-date ")"])
