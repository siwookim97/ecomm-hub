const add_textbox = (obj) => {
  const box = obj.parentElement.parentElement;
  const newP = document.createElement("div");

  newP.innerHTML = "<div class='form-group'><td><input type='text' class='form-control' name='address' ></td><div><button type='button' class='btn btn-primary btn-block'  onclick='opt_remove(this)'>삭제</button></td></div>";
  box.parentNode.insertBefore(newP, box.nextSibling);
}
const opt_remove = (obj) => {
  obj.parentElement.parentElement.parentElement.removeChild(obj.parentElement.parentElement);
}