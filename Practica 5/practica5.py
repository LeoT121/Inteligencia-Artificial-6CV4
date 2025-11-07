import pandas as pd
from sklearn.model_selection import train_test_split, cross_val_score, StratifiedKFold
from sklearn.neighbors import NearestCentroid
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import accuracy_score, confusion_matrix, ConfusionMatrixDisplay
import matplotlib.pyplot as plt

df = pd.read_csv("ecommerce_customer_behavior_dataset.csv")

print("Columnas:", df.columns)

y = df['Is_Returning_Customer']

X = df.drop(['Is_Returning_Customer', 'Order_ID', 'Customer_ID', 'Date', 'City', 'Payment_Method', 'Device_Type', 'Gender', 'Product_Category'], axis=1)

X = pd.get_dummies(X)


clf = NearestCentroid()


X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42, stratify=y)

clf.fit(X_train, y_train)
y_pred = clf.predict(X_test)

acc_holdout = accuracy_score(y_test, y_pred)
cm_holdout = confusion_matrix(y_test, y_pred)

print("\n--- Hold-Out 70/30 ---")
print(f"Accuracy: {acc_holdout:.4f}")
print("Matriz de Confusión:\n", cm_holdout)

disp = ConfusionMatrixDisplay(confusion_matrix=cm_holdout)
disp.plot(cmap='Blues')
plt.title("Matriz de Confusión - Hold-Out")
plt.show()

cv = StratifiedKFold(n_splits=10, shuffle=True, random_state=42)
cv_scores = cross_val_score(clf, X, y, cv=cv)

print("\n--- 10-Fold Cross Validation ---")
print(f"Accuracy promedio: {cv_scores.mean():.4f}")
print("Accuracy por fold:", cv_scores)
